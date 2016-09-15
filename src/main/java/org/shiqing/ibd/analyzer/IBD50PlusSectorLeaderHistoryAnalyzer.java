package org.shiqing.ibd.analyzer;

import java.util.List;

import org.shiqing.ibd.model.InputSpreadsheet;
import org.shiqing.ibd.model.OutputSpreadsheet;
import org.shiqing.ibd.model.input.IBD50PlusSectorLeaderStockList;
import org.shiqing.ibd.model.output.IBD50AndSectorLeaderStockListAnalyzeResult;
import org.shiqing.ibd.model.output.StockAnalyzeResult;

/**
 * 
 * A history analyzer for all IBD50 + Sector Leader existing analyze result.
 * The purpose of this analyzer is to show which stock is most frequently shown on this results
 * and what the continuity it is.
 * 
 * @author shiqing
 *
 */
public class IBD50PlusSectorLeaderHistoryAnalyzer implements Analyzer {
	
	private IBD50AndSectorLeaderStockListAnalyzeResult result;

	public IBD50PlusSectorLeaderHistoryAnalyzer() {
		super();
		result = new IBD50AndSectorLeaderStockListAnalyzeResult();
	}

	public OutputSpreadsheet analyze(List<InputSpreadsheet> stockLists) {
		for (int i = 0; i < stockLists.size(); i++) {
			// The Spreadsheet here is actually StockList TODO Find a clearer way
			IBD50PlusSectorLeaderStockList stockList = (IBD50PlusSectorLeaderStockList)(stockLists.get(i));
			for (StockAnalyzeResult stock : stockList.getStocks()) {
				result.addStockAnalyzeResult(stock, stockList.getName());
			}
		}
		
		return result;
	}

	public void generateResultSpreadsheet(OutputSpreadsheet result) {
		String fileName = ROOT_DIRECTORY + RESULT_DIRECTORY + "Golden.xls";
		
		AnalyzerUtil.generateGoldenSpreadsheet(result, fileName);
	}

}
