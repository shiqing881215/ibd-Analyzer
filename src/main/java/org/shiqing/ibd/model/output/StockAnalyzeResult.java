package org.shiqing.ibd.model.output;

import java.util.Map;
import java.util.Set;

import org.shiqing.ibd.model.TimePeriod;

import com.google.common.collect.Sets;

/**
 * A data representation of single stock analyze result
 * @author shiqing
 *
 */
// TODO Make it more generic as the first level single stock output pojo
public class StockAnalyzeResult {
	private String symbol;
	private String name;
	private Integer occurrence;
	private Set<String> involvedSpreadsheets;
	private Map<TimePeriod, Double> quotePerformance;
	
	public StockAnalyzeResult() {
		involvedSpreadsheets = Sets.newHashSet();
	}
	
	public StockAnalyzeResult(String symbol, String name, Integer occurrence, Set<String> involvedSpreadsheets) {
		super();
		this.symbol = symbol;
		this.name = name;
		this.occurrence = occurrence;
		this.involvedSpreadsheets = involvedSpreadsheets;
	}

	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getOccurrence() {
		return occurrence;
	}
	public void setOccurrence(Integer occurrence) {
		this.occurrence = occurrence;
	}
	public Set<String> getInvolvedSpreadsheets() {
		return involvedSpreadsheets;
	}
	public void setInvolvedSpreadsheets(Set<String> involvedSpreadsheets) {
		this.involvedSpreadsheets = involvedSpreadsheets;
	}
	public Map<TimePeriod, Double> getQuotePerformance() {
		return quotePerformance;
	}
	public void setQuotePerformance(Map<TimePeriod, Double> quotePerformance) {
		this.quotePerformance = quotePerformance;
	}
}
