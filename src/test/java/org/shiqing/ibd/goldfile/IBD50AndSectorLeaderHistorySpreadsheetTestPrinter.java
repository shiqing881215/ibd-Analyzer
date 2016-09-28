package org.shiqing.ibd.goldfile;

import org.shiqing.ibd.config.ConfigFactory;
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
		String fileName = ConfigFactory.get().getPropertiesProvider().getValue("path.test.result") + "Golden.xls";
		
		generateGoldenSpreadsheet(outputSpreadsheet, fileName);
	}
	
}
