package org.shiqing.ibd.analyzer;

import java.util.List;
import java.util.Map.Entry;

import org.shiqing.ibd.model.InputSpreadsheet;
import org.shiqing.ibd.model.OutputSpreadsheet;
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
	
	// TODO Remove hardcode
	private static final int THRESHOLD = 3;
	private static final String IBD_50 ="IBD 50";
	private static final String SECTOR_LEADERS ="SECTOR LEADERS";

	@Override
	public OutputSpreadsheet analyze(List<InputSpreadsheet> inputSpreadsheets) {
		StockListAnalyzeResult fullAnalyzerResult;
		
		// Check whether the full analyze has been called somewhere to save some time
		if (!super.result.getResult().isEmpty()) {
			fullAnalyzerResult = super.result;
		} else {
			fullAnalyzerResult = (StockListAnalyzeResult)(super.analyze(inputSpreadsheets));
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
}
