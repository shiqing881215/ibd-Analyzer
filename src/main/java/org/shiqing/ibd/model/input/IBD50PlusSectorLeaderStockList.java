package org.shiqing.ibd.model.input;

import java.util.List;

import org.shiqing.ibd.model.InputSpreadsheet;
import org.shiqing.ibd.model.output.StockAnalyzeResult;

import com.google.common.collect.Lists;

/**
 * 
 * Data pojo to represent a result spreadsheet, e.g MM_dd_yy_ibd50_plus_sector_leader.xls
 * 
 * @author shiqing
 *
 */
public class IBD50PlusSectorLeaderStockList implements InputSpreadsheet {
	private String name;   // The result spreadsheet name
	private List<StockAnalyzeResult> stocks;  // The stock listed in this result spreadsheet
	
	public IBD50PlusSectorLeaderStockList() {
		stocks = Lists.newArrayList();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<StockAnalyzeResult> getStocks() {
		return stocks;
	}
	public void setStocks(List<StockAnalyzeResult> stocks) {
		this.stocks = stocks;
	}
	
	public void addStock(StockAnalyzeResult s) {
		stocks.add(s);
	}
}
