package org.shiqing.ibd.analyzer;

import java.util.List;
import java.util.Map.Entry;

import org.shiqing.ibd.model.StockAnalyzeResult;
import org.shiqing.ibd.model.StockList;
import org.shiqing.ibd.model.StockListAnalyzeResult;

/**
 * IBD_50 + Sector_Leader analyzer 
 *     - highlights the stocks that shown both in IBD50 and Sector Leader spreadsheets.
 * 
 * @author shiqing
 *
 */
public class IBD50PlusSectorLeaderAnalyzer extends FullAnalyzer {
	
	private static final String IBD_50 ="IBD 50";
	private static final String SECTOR_LEADERS ="SECTOR LEADERS";

	@Override
	public StockListAnalyzeResult analyze(List<StockList> stockLists) {
		StockListAnalyzeResult fullAnalyzerResult = super.analyze(stockLists);
		
		StockListAnalyzeResult ibd50PlusSectorLeaderAnalyzeResult = new StockListAnalyzeResult();
		
		for (Entry<String, StockAnalyzeResult> singleStockResult : fullAnalyzerResult.getResult().entrySet()) {
			if (singleStockResult.getValue().getInvolvedSpreadsheets().contains(IBD_50) && 
					singleStockResult.getValue().getInvolvedSpreadsheets().contains(SECTOR_LEADERS)) {
				ibd50PlusSectorLeaderAnalyzeResult.addExistingStockAnalyzeResult(singleStockResult);
			}
		}
		
		return ibd50PlusSectorLeaderAnalyzeResult;
	}
}