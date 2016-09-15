package org.shiqing.ibd.model.output;

import java.util.Set;

import com.google.common.collect.Sets;

/**
 * A single stock anaylyze result specific for IBD50 + Sector Leader.
 * 
 * A sample data format : 
 * MXL | Maxlinear.Inc | 3 | 08_21_16, 08_28_16, 09_05_16
 * 
 * @author shiqing
 *
 */
public class IBD50AndSectorLeaderStockAnalyzeResult {
	private String symbol;
	private String name;
	private int occurrence;
	private Set<String> involvedDates;
	// TODO Add an indicator for continuity later
	
	public IBD50AndSectorLeaderStockAnalyzeResult() {
		involvedDates = Sets.newHashSet();
	}

	public IBD50AndSectorLeaderStockAnalyzeResult(String symbol, String name, int occurrence,
			Set<String> involvedDates) {
		super();
		if (involvedDates == null) {
			involvedDates = Sets.newHashSet();
		}
		this.symbol = symbol;
		this.name = name;
		this.occurrence = occurrence;
		this.involvedDates = involvedDates;
	}

	public String getSymbol() {
		return symbol;
	}

	public String getName() {
		return name;
	}

	public int getOccurrence() {
		return occurrence;
	}

	public Set<String> getInvolvedDates() {
		return involvedDates;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOccurrence(int occurrence) {
		this.occurrence = occurrence;
	}

	public void setInvolvedDates(Set<String> involvedDates) {
		this.involvedDates = involvedDates;
	}
}