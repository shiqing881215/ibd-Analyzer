package org.shiqing.ibd.analyzer;

import java.util.List;

import org.shiqing.ibd.model.InputSpreadsheet;
import org.shiqing.ibd.model.OutputSpreadsheet;
import org.shiqing.ibd.model.input.Stock;
import org.shiqing.ibd.model.input.StockList;
import org.shiqing.ibd.model.output.StockListAnalyzeResult;

/**
 * Accumulation / Distribution analyzer.
 * Look for the stocks meet : 
 * 1) Composite rate >= 95
 * 2) EPS >= 80
 * 3) RS rate >= 80
 * 4) SMR = A- | A | A+
 * 5) Acc/Dis = A+
 * 
 * @author shiqing
 * 
 */
public class AccDisAnalyzer implements Analyzer {

	public OutputSpreadsheet analyze(List<InputSpreadsheet> inputSpreadsheets) {
		StockListAnalyzeResult result = new StockListAnalyzeResult();
		
		// First update the context
		AnalyzerUtil.updateContext(this.getClass().getSimpleName());
		
		for (int i = 0; i < inputSpreadsheets.size(); i++) {
			StockList stockList = (StockList)(inputSpreadsheets.get(i));
			for (Stock stock : stockList.getStocks()) {
				if (stock.getCompositeRating() >= 95 &&
						stock.getEPSRating() >= 80 &&
						stock.getRSRating() >= 80 &&
						stock.getSMRRating().contains("A") && 
						stock.getACC_DISRating().equals("A+")) {
					result.addStockAnalyzeResult(stock, stockList.getName());
				}
			}
		}
		
		return result;
	}

}
