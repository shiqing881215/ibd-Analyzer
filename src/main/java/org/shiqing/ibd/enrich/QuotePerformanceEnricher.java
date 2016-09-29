package org.shiqing.ibd.enrich;

import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.shiqing.ibd.config.ConfigFactory;
import org.shiqing.ibd.exception.QuoteException;
import org.shiqing.ibd.model.OutputSpreadsheet;
import org.shiqing.ibd.model.TimePeriod;
import org.shiqing.ibd.model.output.IBD50AndSectorLeaderStockAnalyzeResult;
import org.shiqing.ibd.model.output.IBD50AndSectorLeaderStockListAnalyzeResult;
import org.shiqing.ibd.model.output.StockListAnalyzeResult;
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
	private final static Logger logger = Logger.getLogger(QuotePerformanceEnricher.class);
	
	public QuotePerformanceEnricher() {
		quoteService = (QuoteService)ConfigFactory.get().getBean("quoteService");
	}

	/**
	 * Set performance quote for each stock
	 */
	public OutputSpreadsheet enrich(OutputSpreadsheet outputSpreadsheet) {
		// Update the context first
		EnricherUtil.updateContext(this.getClass().getSimpleName());
		
		if (outputSpreadsheet instanceof StockListAnalyzeResult) {
			StockListAnalyzeResult result = (StockListAnalyzeResult)outputSpreadsheet;
		
			// TODO ............
			
			return result;
		} else if (outputSpreadsheet instanceof IBD50AndSectorLeaderStockListAnalyzeResult) {
			IBD50AndSectorLeaderStockListAnalyzeResult result = (IBD50AndSectorLeaderStockListAnalyzeResult)outputSpreadsheet;
			
			// TODO Need also work for first level result
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
		
		return null;
	}

	private double getQuotePerformance(String symbol, TimePeriod timePeriod) {
		
		Pair<Double, Double> quotes;
		try {
			quotes = quoteService.getHistoryQuotes(symbol, timePeriod);
		} catch (QuoteException e) {
			logger.error("Fail to get quote performance for " + symbol + " for " + timePeriod.getName(), e);
			return Double.NaN;
		}
		log(symbol, quotes, timePeriod);
		
		return (quotes.getLeft() - quotes.getRight()) / quotes.getRight();
	}
	
	private void log(String symbol, Pair<Double, Double> quotes, TimePeriod timePeriod) {
		logger.info("Get " + symbol + " for " + timePeriod.getNumberOfWeeks() + " weeks");
		logger.info("Current price is " + quotes.getLeft());
		logger.info("Old price is " + quotes.getRight());
	}
	
	public static void main(String[] args) {
		QuotePerformanceEnricher q = new QuotePerformanceEnricher();
		System.out.println(q.getQuotePerformance("CRM", TimePeriod.ONE_MONTH));
	}
}
