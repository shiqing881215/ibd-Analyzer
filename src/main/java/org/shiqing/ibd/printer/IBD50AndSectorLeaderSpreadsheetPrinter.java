package org.shiqing.ibd.printer;

import org.shiqing.ibd.model.OutputSpreadsheet;

/**
 * Printer for IBD50 + Sector Leader analyze result.
 * 
 * @author shiqing
 *
 */
public class IBD50AndSectorLeaderSpreadsheetPrinter implements SpreadsheetPrinter {

	public void generateResultSpreadsheet(OutputSpreadsheet outputSpreadsheet) {
		String fileName = SpreadsheetPrinterUtil.RESULT_DIRECTORY + 
				SpreadsheetPrinterUtil.getPrintDate() + "_ibd50_plus_sector_leader" + ".xls";
		
		SpreadsheetPrinterUtil.generateResultSpreadsheet(outputSpreadsheet, false, fileName);
	}

}
