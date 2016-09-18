package org.shiqing.ibd.services;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.shiqing.ibd.model.TimePeriod;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

/**
 * A service to retrieve stock quote.
 * 
 * @author shiqing
 *
 */
public class QuoteService {
	
	private static QuoteService  quoteService;
	
	private QuoteService() {
		
	}
	
	public static QuoteService getService() {
		if (quoteService == null) {
			quoteService = new QuoteService();
		}
		return quoteService;
	}
	
	public double getQuote(String symbol, int year, int month, int day) {
		Calendar from = Calendar.getInstance();
		Calendar to = Calendar.getInstance();
		from.set(year, month, day);
		to.set(year, month, day);
		
		Stock stock = YahooFinance.get(symbol, true);
		List<HistoricalQuote> histQuotes = stock.getHistory(from, to, Interval.DAILY);
		
		return histQuotes.get(0).getClose().doubleValue();
	}
	
	public Pair<Double, Double> getHistoryQuotes(String symbol, TimePeriod timePeriod) {
		Calendar from = Calendar.getInstance();
		Calendar to = Calendar.getInstance();
		from.add(Calendar.WEEK_OF_YEAR, -timePeriod.getNumberOfWeeks());
		
		Stock stock = YahooFinance.get(symbol, true);
		final List<HistoricalQuote> historyQuotes = stock.getHistory(from, to, Interval.DAILY);
		
		return new Pair<Double, Double>() {
			
			private static final long serialVersionUID = 1L;

			public Double setValue(Double value) {
				return null;
			}
			
			@Override
			public Double getLeft() {
				return historyQuotes.get(0).getClose().doubleValue();
			}
			
			@Override
			public Double getRight() {
				return historyQuotes.get(historyQuotes.size()-1).getClose().doubleValue();
			}
		};
	}
	
	public static void main(String[] args) {
		/*
		Calendar from = Calendar.getInstance();
		Calendar to = Calendar.getInstance();
//		from.add(Calendar.DAY_OF_YEAR, -2); // from 1 year ago
		from.set(2016, 8, 16);
		to.set(2016, 8, 16);
		
		Stock stock = YahooFinance.get("CRM", true);
		
		List<HistoricalQuote> googleHistQuotes = stock.getHistory(from, to, Interval.DAILY);
		 
//		BigDecimal price = stock.getQuote().getPrice();
//		BigDecimal change = stock.getQuote().getChangeInPercent();
//		BigDecimal peg = stock.getStats().getPeg();
//		BigDecimal dividend = stock.getDividend().getAnnualYieldPercent();
		 
		stock.print();
		System.out.println(googleHistQuotes.get(0).getClose());  */
		
		System.out.println(QuoteService.getService().getHistoryQuotes("CRM", TimePeriod.ONE_MONTH));
	}
}
