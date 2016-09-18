package org.shiqing.ibd.model;

public enum TimePeriod {
	ONE_WEEK("one week", 1, 0),
	ONE_MONTH("one month", 5, 1),
	THREE_MONTHS("three months", 13, 3),
	SIX_MONTHS("six months", 26, 6),
	ALL("all", -1, -1);
	
	private String name;
	private int numberOfWeeks;
	private int numberOfMonths;
	
	TimePeriod(String name, int numberOfWeeks, int numberOfMonths) {
		this.name = name;
		this.numberOfWeeks = numberOfWeeks;
		this.numberOfMonths = numberOfMonths;
	}

	public String getName() {
		return name;
	}

	public int getNumberOfWeeks() {
		return numberOfWeeks;
	}
	
	public int getNumberOfMonths() {
		return numberOfMonths;
	}
}
