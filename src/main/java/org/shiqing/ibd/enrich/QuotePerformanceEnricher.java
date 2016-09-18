package org.shiqing.ibd.enrich;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.commons.lang3.tuple.Pair;
import org.shiqing.ibd.model.OutputSpreadsheet;
import org.shiqing.ibd.model.TimePeriod;
import org.shiqing.ibd.model.output.IBD50AndSectorLeaderStockAnalyzeResult;
import org.shiqing.ibd.model.output.IBD50AndSectorLeaderStockListAnalyzeResult;
import org.shiqing.ibd.services.QuoteService;

/**
 * Add quote performance for each stock.
 * The way we calculate this is : 
 * (Current Price - Old Price) / Old Price
 * Old price is based on the time period between current date. 
 * 
 * @author shiqing
 *
 */
public class QuotePerformanceEnricher implements Enricher {

	/**
	 * Set performance quote for each stock
	 */
	public OutputSpreadsheet enrich(OutputSpreadsheet outputSpreadsheet) {
		IBD50AndSectorLeaderStockListAnalyzeResult result = (IBD50AndSectorLeaderStockListAnalyzeResult)outputSpreadsheet;
		
		Iterator<Entry<String, IBD50AndSectorLeaderStockAnalyzeResult>> iterator = result.getResult().entrySet().iterator();
		while(iterator.hasNext()) {
			IBD50AndSectorLeaderStockAnalyzeResult singleStockResult = iterator.next().getValue();
			double quotePerformance = getQuotePerformance(singleStockResult.getSymbol(), result.getTimePeriod());
			singleStockResult.setQuotePerformance(quotePerformance);
		}
		
		return result;
	}

	private double getQuotePerformance(String symbol, TimePeriod timePeriod) {
		Pair<Double, Double> quotes = QuoteService.getService().getHistoryQuotes(symbol, timePeriod);
		log(symbol, quotes, timePeriod);
		
		return (quotes.getLeft() - quotes.getRight()) / quotes.getRight();
	}
	
	private void log(String symbol, Pair<Double, Double> quotes, TimePeriod timePeriod) {
		System.out.println("---------------------");
		System.out.println(symbol);
		System.out.println("Timeperiod is " + timePeriod.getNumberOfWeeks() + " weeks");
		System.out.println("Current price is " + quotes.getLeft());
		System.out.println("Old price is " + quotes.getRight());
		System.out.println("---------------------");
	}
	
	public static void main(String[] args) {
		System.out.println((41.48-38.099998)/38.099998);
		System.out.println(MessageFormat.format("{0,number,#.##%}", (41.48-38.099998)/38.099998));
	}
}
