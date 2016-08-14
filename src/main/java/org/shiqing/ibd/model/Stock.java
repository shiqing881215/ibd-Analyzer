package org.shiqing.ibd.model;

public class Stock {
	private String symbol;
	private String companyName;
	private Double price;
	private Integer compositeRating;
	private Integer EPSRating;
	private Integer RSRating;
	private String SMRRating;
	private String ACC_DISRating;
	
	public Stock(String symbol, String companyName, Double price, Integer compositeRating, Integer ePSRating,
			Integer rSRating, String sMRRating, String aCC_DISRating) {
		super();
		this.symbol = symbol;
		this.companyName = companyName;
		this.price = price;
		this.compositeRating = compositeRating;
		EPSRating = ePSRating;
		RSRating = rSRating;
		SMRRating = sMRRating;
		ACC_DISRating = aCC_DISRating;
	}
	
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getCompositeRating() {
		return compositeRating;
	}
	public void setCompositeRating(Integer compositeRating) {
		this.compositeRating = compositeRating;
	}
	public Integer getEPSRating() {
		return EPSRating;
	}
	public void setEPSRating(Integer ePSRating) {
		EPSRating = ePSRating;
	}
	public Integer getRSRating() {
		return RSRating;
	}
	public void setRSRating(Integer rSRating) {
		RSRating = rSRating;
	}
	public String getSMRRating() {
		return SMRRating;
	}
	public void setSMRRating(String sMRRating) {
		SMRRating = sMRRating;
	}
	public String getACC_DISRating() {
		return ACC_DISRating;
	}
	public void setACC_DISRating(String aCC_DISRating) {
		ACC_DISRating = aCC_DISRating;
	}
}
