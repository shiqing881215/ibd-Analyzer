package org.shiqing.ibd.goldfile;

import org.shiqing.ibd.config.ConfigFactory;
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
		String fileName = ConfigFactory.get().getPropertiesProvider().getValue("path.test.result") + "ibd50_plus_sector_leader.xls";
		
		PrinterUtil.generateResultSpreadsheet(outputSpreadsheet, fileName);
	}
	
}
