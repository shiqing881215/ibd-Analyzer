package org.shiqing.ibd.analyzer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import org.shiqing.ibd.model.Spreadsheet;
import org.shiqing.ibd.model.output.StockAnalyzeResult;
import org.shiqing.ibd.model.output.StockListAnalyzeResult;

/**
 * 
 * List out all the stocks that have more than or equal to threshold number in occurrence.
 * Exempt the stocks which involved in both IBD50 and SectorLeader.  
 * 
 * @author shiqing
 *
 */
public class HighOccurrenceAnalyzer extends FullAnalyzer {
	
	private static final int THRESHOLD = 3;
	private static final String IBD_50 ="IBD 50";
	private static final String SECTOR_LEADERS ="SECTOR LEADERS";

	@Override
	public StockListAnalyzeResult analyze(List<Spreadsheet> stockLists) {
		StockListAnalyzeResult fullAnalyzerResult;
		
		// Check whether the full analyze has been called somewhere to save some time
		if (!super.result.getResult().isEmpty()) {
			fullAnalyzerResult = super.result;
		} else {
			fullAnalyzerResult = super.analyze(stockLists);
		}
		
		StockListAnalyzeResult highOccurrenceAnalyzeResult = new StockListAnalyzeResult();
		
		for (Entry<String, StockAnalyzeResult> singleStockResult : fullAnalyzerResult.getResult().entrySet()) {
			if (singleStockResult.getValue().getOccurrence() >= THRESHOLD && 
				!(singleStockResult.getValue().getInvolvedSpreadsheets().contains(IBD_50) && 
				singleStockResult.getValue().getInvolvedSpreadsheets().contains(SECTOR_LEADERS)) ) {
				highOccurrenceAnalyzeResult.addExistingStockAnalyzeResult(singleStockResult);
			}
		}
		
		return highOccurrenceAnalyzeResult;
	}

	@Override
	public void generateResultSpreadsheet(StockListAnalyzeResult result) {
		DateFormat df = new SimpleDateFormat("MM_dd_yy");
		String fileName = ROOT_DIRECTORY + RESULT_DIRECTORY + 
				df.format(new Date()) + "_high_occurence" + ".xls";
		
		AnalyzerUtil.generateResultSpreadsheet(result, false, fileName);
	}
	
	
}
