package org.shiqing.ibd.analyzer;

import java.util.List;

import org.shiqing.ibd.model.InputSpreadsheet;
import org.shiqing.ibd.model.OutputSpreadsheet;
import org.shiqing.ibd.model.input.Stock;
import org.shiqing.ibd.model.input.StockList;
import org.shiqing.ibd.model.output.StockListAnalyzeResult;

/**
 * Full analyzer 
 *     - print all the info of each stock shown in the all stock lists
 *     - highlights the stocks that have equal or more than 3 in occurrence
 * 
 * @author shiqing
 *
 */
public class FullAnalyzer implements Analyzer {
	
	// Keep a local copy of analyze result
	protected StockListAnalyzeResult result;
	
	public FullAnalyzer() {
		super();
		this.result = new StockListAnalyzeResult();
	}

	public OutputSpreadsheet analyze(List<InputSpreadsheet> stockLists) {
		for (int i = 0; i < stockLists.size(); i++) {
			StockList stockList = (StockList)(stockLists.get(i));
			for (Stock stock : stockList.getStocks()) {
				result.addStockAnalyzeResult(stock, stockList.getName());
			}
		}
		
		return result;
	}
}
