package org.shiqing.ibd.goldfile;

import org.shiqing.ibd.model.OutputSpreadsheet;
import org.shiqing.ibd.printer.IBD50AndSectorLeaderHistorySpreadsheetPrinter;

/**
 * 
 * Test implementation of {@link IBD50AndSectorLeaderHistorySpreadsheetPrinter}.
 * Major purpose here is to override the output file directory.
 * 
 * @author shiqing
 *
 */
public class IBD50AndSectorLeaderHistorySpreadsheetTestPrinter extends IBD50AndSectorLeaderHistorySpreadsheetPrinter {

	@Override
	public void generateResultSpreadsheet(OutputSpreadsheet outputSpreadsheet) {
		String fileName = "/Users/Rossi/Documents/workspace/ibd/src/test/java/org/shiqing/ibd/goldfile/raw/results/Golden.xls";
		
		generateGoldenSpreadsheet(outputSpreadsheet, fileName);
	}
	
}
