package org.shiqing.ibd.printer;

import org.shiqing.ibd.model.OutputSpreadsheet;

/**
 * Printer for IBD50 + Sector Leader history analyze result.
 * 
 * @author shiqing
 *
 */
public class IBD50AndSectorLeaderHistorySpreadsheetPrinter implements SpreadsheetPrinter {

	public void generateResultSpreadsheet(OutputSpreadsheet outputSpreadsheet) {
		String fileName = ROOT_DIRECTORY + RESULT_DIRECTORY + "Golden.xls";
		
		SpreadsheetPrinterUtil.generateGoldenSpreadsheet(outputSpreadsheet, fileName);
	}

}
