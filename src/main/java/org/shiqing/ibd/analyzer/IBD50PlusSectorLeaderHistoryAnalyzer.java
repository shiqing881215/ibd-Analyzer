package org.shiqing.ibd.analyzer;

import java.util.List;

import org.shiqing.ibd.model.StockList;
import org.shiqing.ibd.model.StockListAnalyzeResult;

/**
 * 
 * A history analyzer for all IBD50 + Sector Leader existing analyze result.
 * The purpose of this analyzer is to show which stock is most frequently shown on this results
 * and what the continuity it is.
 * 
 * @author shiqing
 *
 */
public class IBD50PlusSectorLeaderHistoryAnalyzer extends FullAnalyzer {

	@Override
	public StockListAnalyzeResult analyze(List<StockList> stockLists) {
		// TODO Auto-generated method stub
		return super.analyze(stockLists);
	}

	@Override
	public void generateResultSpreadsheet(StockListAnalyzeResult result) {
		// TODO Auto-generated method stub
		super.generateResultSpreadsheet(result);
	}

}
