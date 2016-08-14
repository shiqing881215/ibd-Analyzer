package org.shiqing.ibd.model;

import java.util.List;

import com.google.common.collect.Lists;

public class StockList {
	private String name;
	private List<Stock> stocks;
	
	public StockList() {
		stocks = Lists.newArrayList();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Stock> getStocks() {
		return stocks;
	}
	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
	}
	
	public void addStock(Stock stock) {
		stocks.add(stock);
	}
}
