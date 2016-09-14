package org.shiqing.ibd.analyzer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.shiqing.ibd.model.raw.Stock;
import org.shiqing.ibd.model.raw.StockList;
import org.shiqing.ibd.model.raw.StockListAnalyzeResult;

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

	public StockListAnalyzeResult analyze(List<StockList> stockLists) {
		for (StockList stockList : stockLists) {
			for (Stock stock : stockList.getStocks()) {
				result.addStockAnalyzeResult(stock, stockList.getName());
			}
		}
		
		return result;
	}

	public void generateResultSpreadsheet(StockListAnalyzeResult result) {
		DateFormat df = new SimpleDateFormat("MM_dd_yy");
		String fileName = ROOT_DIRECTORY + RESULT_DIRECTORY + df.format(new Date()) + ".xls";
		
		AnalyzerUtil.generateResultSpreadsheet(result, true, fileName);
	}
}
