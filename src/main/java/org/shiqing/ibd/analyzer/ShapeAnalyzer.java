package org.shiqing.ibd.analyzer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.shiqing.ibd.model.InputSpreadsheet;
import org.shiqing.ibd.model.OutputSpreadsheet;
import org.shiqing.ibd.model.input.Stock;
import org.shiqing.ibd.model.input.StockList;
import org.shiqing.ibd.model.output.StockListAnalyzeResult;

import com.google.common.collect.Lists;

import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

/**
 * Analyzer for stock shape
 * 
 * @author shiqing
 *
 */
public class ShapeAnalyzer implements Analyzer {

	public OutputSpreadsheet analyze(List<InputSpreadsheet> inputSpreadsheets) {
		// First update the context
		AnalyzerUtil.updateContext(this.getClass().getSimpleName());
		
		StockListAnalyzeResult result = new StockListAnalyzeResult();
		
		for (InputSpreadsheet inputSpreadsheet : inputSpreadsheets) {
			StockList stockList = (StockList)inputSpreadsheet;
			for (Stock stock : stockList.getStocks()) {
				if (isCupShape(stock.getSymbol())) {
					result.addStockAnalyzeResult(stock, stockList.getName());
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Detect whether the stock is a good cup shape given the historical quotes.
	 * 
	 * 04/06/17 - Right now I have two raw rules to define the cup shape. TODO A better way to detect cup shape (machine learning ???)
	 * 
	 * @param quotes - history quote ordering from latest (current) to oldest
	 * @return false/true based on whether stock is in a cup shape
	 */
	private boolean isCupShape(String stockSymbol) {
		yahoofinance.Stock stock = YahooFinance.get(stockSymbol, true);
		
		// Retrieve back for 1 year data
		Calendar from = Calendar.getInstance();
		Calendar to = Calendar.getInstance();
		from.add(Calendar.WEEK_OF_YEAR, -52);
		List<HistoricalQuote> quotes = stock.getHistory(from, to, Interval.DAILY);
		
		HistoricalQuote high = null, low = null, current = quotes.get(0);
		Double highPrice = Double.MIN_VALUE, lowPrice = Double.MAX_VALUE, currentPrice = current.getClose().doubleValue(); 
		
		for (HistoricalQuote quote : quotes) {
			if (quote.getClose().doubleValue() > highPrice) {
				highPrice = quote.getClose().doubleValue();
				high = quote;
			}
			
			if (quote.getClose().doubleValue() < lowPrice) {
				lowPrice = quote.getClose().doubleValue();
				low = quote;
			}
		}
		
		// If it's just past the highest recently, we want to see whether there is a "similar" high price point before
		List<HistoricalQuote> highCandidates = Lists.newArrayList();
		if (current.getClose().doubleValue() == highPrice || 
				((highPrice - current.getClose().doubleValue())/highPrice < 0.05 && TimeUnit.DAYS.convert(Math.abs(high.getDate().getTimeInMillis() - current.getDate().getTimeInMillis()), TimeUnit.MILLISECONDS) < 10)) {
			for (HistoricalQuote quote : quotes) {
				if ((highPrice-quote.getClose().doubleValue())/highPrice < 0.05 && 
						TimeUnit.DAYS.convert(Math.abs(quote.getDate().getTimeInMillis() - current.getDate().getTimeInMillis()), TimeUnit.MILLISECONDS) >20) {
					highCandidates.add(quote);
				}
			}
		}
		
		// If not empty, update high quote to the previous similar one which has the highest price, 
		// instead of recently real highest price quote
		if (!highCandidates.isEmpty()) {
			high = highCandidates.get(0);
			highPrice = highCandidates.get(0).getClose().doubleValue();
			for (HistoricalQuote quote : highCandidates) {
				if (quote.getClose().doubleValue() > highPrice) {
					high = quote;
					highPrice = quote.getClose().doubleValue();
				}
			}
		}
		
		// Print out adjuested information
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println("Highest quote is " + highPrice + " on " + format.format(high.getDate().getTime()));
		System.out.println("Lowest quote is " + lowPrice + " on " + format.format(low.getDate().getTime()));
		System.out.println("Current quote is " + currentPrice + " on " + format.format(current.getDate().getTime()));
		System.out.println("Price change is " + (highPrice - currentPrice) / currentPrice * 100 + "%");
		System.out.println("Time period is " + TimeUnit.DAYS.convert(Math.abs(high.getDate().getTimeInMillis() - current.getDate().getTimeInMillis()), TimeUnit.MILLISECONDS) + " days");
		
		
		// ------------------------------- Apply some rules below -------------------------------
		
		// Rule 1 :
		// Make sure the current price is within +/- 10% of high price
		// And gap between two dates is more than 20 days
		if (Math.abs((highPrice - current.getClose().doubleValue())/highPrice) > 0.1 || 
				TimeUnit.DAYS.convert(Math.abs(high.getDate().getTimeInMillis() - current.getDate().getTimeInMillis()), TimeUnit.MILLISECONDS) < 20) {
			System.out.println("FAILING REASON ********** Not in a good relationship with highest price : "
					+ "Current prices is " + current.getClose().doubleValue() 
					+ "; highest prices is " + highPrice 
					+ "; Difference is " + (highPrice - current.getClose().doubleValue())/highPrice
					+ "; time gap is " + TimeUnit.DAYS.convert(Math.abs(high.getDate().getTimeInMillis() - current.getDate().getTimeInMillis()), TimeUnit.MILLISECONDS));
			return false;
		}
		
		// Rule 2 : 
		// Make sure current trend in on the right side instead of the left side
		// The cup should at least more than 10% down
		int highIndex = quotes.indexOf(high);
		double lowestWithinRangePrice = highPrice;
		HistoricalQuote lowestWithinRange = null;
		for (int i = 0; i < highIndex; i++) {
			if (quotes.get(i).getClose().doubleValue() < lowestWithinRangePrice) {
				lowestWithinRangePrice = quotes.get(i).getClose().doubleValue();
				lowestWithinRange = quotes.get(i);
			}
		}
		
		if (lowestWithinRangePrice == current.getClose().doubleValue() || 
				(current.getClose().doubleValue() - lowestWithinRangePrice) < (highPrice - lowestWithinRangePrice)/2 || 
				((highPrice-lowestWithinRangePrice)/highPrice < 0.1)) {
			System.out.println("FAILING REASON ********** Not on the right up side or cup shape is not deep enough : "
					+ "Current prices is " + current.getClose().doubleValue() 
					+ "; highest prices is " + highPrice 
					+ "; lowest price between two is " +lowestWithinRangePrice);
			return false;
		}
		
		return true;
	}

}
