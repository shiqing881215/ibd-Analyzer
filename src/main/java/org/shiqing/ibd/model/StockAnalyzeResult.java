package org.shiqing.ibd.model;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * A data representation of single stock analyze result
 * @author shiqing
 *
 */
public class StockAnalyzeResult {
	private String symbol;
	private String name;
	private Integer occurance;
	private List<String> involvedSpreadsheets;
	
	public StockAnalyzeResult() {
		involvedSpreadsheets = Lists.newArrayList();
	}
	
	public StockAnalyzeResult(String symbol, String name, Integer occurance, List<String> involvedSpreadsheets) {
		super();
		this.symbol = symbol;
		this.name = name;
		this.occurance = occurance;
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
	public Integer getOccurance() {
		return occurance;
	}
	public void setOccurance(Integer occurance) {
		this.occurance = occurance;
	}
	public List<String> getInvolvedSpreadsheets() {
		return involvedSpreadsheets;
	}
	public void setInvolvedSpreadsheets(List<String> involvedSpreadsheets) {
		this.involvedSpreadsheets = involvedSpreadsheets;
	}
}
