package org.shiqing.ibd.model.output;

import java.util.Set;

import com.google.common.collect.Sets;

/**
 * A single stock anaylyze result specific for IBD50 + Sector Leader.
 * 
 * A sample data format : 
 * MXL | Maxlinear.Inc | 3 | 08_21_16, 08_28_16, 09_05_16 | **//***//***** | 1.61% | 5.71% | 12.96% | 37.84%
 * 
 * @author shiqing
 *
 */
// TODO Make it more generic as the second level single stock output pojo
public class IBD50AndSectorLeaderStockAnalyzeResult {
	private String symbol;
	private String name;
	private int occurrence;
	private Set<String> involvedDates;
	private String continuity;
	private double oneWeekPerformance;
	private double oneMonthPerformance;
	private double threeMonthsPerformance;
	private double sixMonthsPerformance;
	
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
	
	public IBD50AndSectorLeaderStockAnalyzeResult(String symbol, String name, int occurrence, Set<String> involvedDates,
			double oneWeekPerformance, double oneMonthPerformance, double threeMonthsPerformance, double sixMonthsPerformance) {
		super();
		this.symbol = symbol;
		this.name = name;
		this.occurrence = occurrence;
		this.involvedDates = involvedDates;
		this.oneWeekPerformance = oneWeekPerformance;
		this.oneMonthPerformance = oneMonthPerformance;
		this.threeMonthsPerformance = threeMonthsPerformance;
		this.sixMonthsPerformance = sixMonthsPerformance;
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
	
	public String getContinuity() {
		return continuity;
	}

	public double getOneWeekPerformance() {
		return oneWeekPerformance;
	}

	public double getOneMonthPerformance() {
		return oneMonthPerformance;
	}

	public double getThreeMonthsPerformance() {
		return threeMonthsPerformance;
	}
	
	public double getSixMonthsPerformance() {
		return sixMonthsPerformance;
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

	public void setContinuity(String continuity) {
		this.continuity = continuity;
	}

	public void setOneWeekPerformance(double oneWeekPerformance) {
		this.oneWeekPerformance = oneWeekPerformance;
	}
	
	public void setOneMonthPerformance(double oneMonthPerformance) {
		this.oneMonthPerformance = oneMonthPerformance;
	}
	
	public void setThreeMonthsPerformance(double threeMonthsPerformance) {
		this.threeMonthsPerformance = threeMonthsPerformance;
	}
	
	public void setSixMonthsPerformance(double sixMonthsPerformance) {
		this.sixMonthsPerformance = sixMonthsPerformance;
	}
}