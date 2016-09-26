package org.shiqing.ibd.analyzer;

import java.util.List;

import org.shiqing.ibd.model.InputSpreadsheet;
import org.shiqing.ibd.model.OutputSpreadsheet;
import org.shiqing.ibd.model.input.Stock;
import org.shiqing.ibd.model.input.StockList;
import org.shiqing.ibd.model.output.StockListAnalyzeResult;

/**
 * 
 * Analyzer for IPO data.
 * 
 * @author shiqing
 *
 */
public class IPOAnalyzer implements Analyzer {

	/**
	 * Right now it just map the java pojo.
	 * It's pretty much same as FullAnalyzer, but later we can add additional analyze here.
	 */
	public OutputSpreadsheet analyze(List<InputSpreadsheet> inputSpreadsheets) {
		StockListAnalyzeResult result = new StockListAnalyzeResult();
		
		for (InputSpreadsheet inputSpreadsheet : inputSpreadsheets) {
			StockList stockList = (StockList)inputSpreadsheet;
			for (Stock stock : stockList.getStocks()) {
				result.addStockAnalyzeResult(stock, stockList.getName());
			}
		}
		
		return result;
	}

}
