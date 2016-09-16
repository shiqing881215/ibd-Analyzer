package org.shiqing.ibd.printer;

import org.shiqing.ibd.model.OutputSpreadsheet;

/**
 * Printer for full analyze result.
 * 
 * @author shiqing
 *
 */
public class FullSpreadsheetPrinter implements SpreadsheetPrinter {

	public void generateResultSpreadsheet(OutputSpreadsheet outputSpreadsheet) {
		String fileName = SpreadsheetPrinterUtil.RESULT_DIRECTORY 
				+ SpreadsheetPrinterUtil.getPrintDate() + ".xls";
		
		SpreadsheetPrinterUtil.generateResultSpreadsheet(outputSpreadsheet, true, fileName);
	}

}
