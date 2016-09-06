package org.shiqing.ibd.analyzer;

import java.util.List;

import org.shiqing.ibd.model.Stock;
import org.shiqing.ibd.model.StockList;
import org.shiqing.ibd.model.StockListAnalyzeResult;

/**
 * Full analyzer 
 *     - print all the info of each stock shown in the all stock lists
 *     - highlights the stocks that have equal or more than 3 in occurrence
 * 
 * @author shiqing
 *
 */
public class FullAnalyzer implements Analyzer {

	public StockListAnalyzeResult analyze(List<StockList> stockLists) {
		StockListAnalyzeResult result = new StockListAnalyzeResult();
		
		for (StockList stockList : stockLists) {
			for (Stock stock : stockList.getStocks()) {
				result.addStockAnalyzeResult(stock, stockList.getName());
			}
		}
		
		return result;
	}
}
