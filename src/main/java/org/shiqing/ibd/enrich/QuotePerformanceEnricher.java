package org.shiqing.ibd.enrich;

import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.commons.lang3.tuple.Pair;
import org.shiqing.ibd.config.ConfigFactory;
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
	
	private QuoteService quoteService;
	
	public QuotePerformanceEnricher() {
		quoteService = (QuoteService)ConfigFactory.get().getBean("quoteService");
	}

	/**
	 * Set performance quote for each stock
	 */
	public OutputSpreadsheet enrich(OutputSpreadsheet outputSpreadsheet) {
		IBD50AndSectorLeaderStockListAnalyzeResult result = (IBD50AndSectorLeaderStockListAnalyzeResult)outputSpreadsheet;
		
		Iterator<Entry<String, IBD50AndSectorLeaderStockAnalyzeResult>> iterator = result.getResult().entrySet().iterator();
		while(iterator.hasNext()) {
			IBD50AndSectorLeaderStockAnalyzeResult singleStockResult = iterator.next().getValue();
			double oneWeekPerformance = getQuotePerformance(singleStockResult.getSymbol(), TimePeriod.ONE_WEEK);
			double oneMonthPerformance = getQuotePerformance(singleStockResult.getSymbol(), TimePeriod.ONE_MONTH);
			double threeMonthsPerformance = getQuotePerformance(singleStockResult.getSymbol(), TimePeriod.THREE_MONTHS);
			double sixMonthsPerformance = getQuotePerformance(singleStockResult.getSymbol(), TimePeriod.SIX_MONTHS);
			
			singleStockResult.setOneWeekPerformance(oneWeekPerformance);
			singleStockResult.setOneMonthPerformance(oneMonthPerformance);
			singleStockResult.setThreeMonthsPerformance(threeMonthsPerformance);
			singleStockResult.setSixMonthsPerformance(sixMonthsPerformance);
		}
		
		return result;
	}

	private double getQuotePerformance(String symbol, TimePeriod timePeriod) {
		Pair<Double, Double> quotes = quoteService.getHistoryQuotes(symbol, timePeriod);
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
		QuotePerformanceEnricher q = new QuotePerformanceEnricher();
		System.out.println(q.getQuotePerformance("CRM", TimePeriod.ONE_MONTH));
	}
}
