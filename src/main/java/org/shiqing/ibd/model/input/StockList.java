package org.shiqing.ibd.model.input;

import java.util.List;

import org.shiqing.ibd.model.Spreadsheet;

import com.google.common.collect.Lists;

/**
 * 
 * Data pojo to represent a raw spreadsheet from IBD, e.g IBD 50.xls
 * 
 * @author shiqing
 *
 */
public class StockList implements Spreadsheet {
	private String name;  // Spreadsheet name
	private List<Stock> stocks;  // List of stocks in this spreadsheet
	
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
