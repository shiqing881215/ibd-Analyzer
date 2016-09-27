package org.shiqing.ibd.goldfile;

import org.shiqing.ibd.model.OutputSpreadsheet;
import org.shiqing.ibd.printer.IBD50AndSectorLeaderSpreadsheetPrinter;
import org.shiqing.ibd.printer.PrinterUtil;

/**
 * 
 * Test implementation of {@link IBD50AndSectorLeaderSpreadsheetPrinter}.
 * Major purpose here is to override the output file directory.
 * 
 * @author shiqing
 *
 */
public class IBD50AndSectorLeaderSpreadsheetTestPrinter extends IBD50AndSectorLeaderSpreadsheetPrinter {

	@Override
	public void generateResultSpreadsheet(OutputSpreadsheet outputSpreadsheet) {
		String fileName = "/Users/Rossi/Documents/workspace/ibd/src/test/java/org/shiqing/ibd/goldfile/raw/results/ibd50_plus_sector_leader.xls";
		
		PrinterUtil.generateResultSpreadsheet(outputSpreadsheet, false, fileName);
	}
	
}
