package org.shiqing.ibd.analyzer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.shiqing.ibd.model.Spreadsheet;
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

	public StockListAnalyzeResult analyze(List<Spreadsheet> stockLists) {
		for (int i = 0; i < stockLists.size(); i++) {
			// The Spreadsheet here is actually StockList TODO Find a clearer way
			StockList stockList = (StockList)(stockLists.get(i));
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
