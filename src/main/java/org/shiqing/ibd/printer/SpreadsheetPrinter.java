package org.shiqing.ibd.printer;

import org.shiqing.ibd.model.OutputSpreadsheet;

/**
 * Interface defining generating the final result spreadsheet.
 * 
 * @author shiqing
 *
 */
public interface SpreadsheetPrinter {
	
	/**
	 * Generate a spreadsheet that contains final the analyze result
	 * @param stockListAnalyzeResult
	 */
	public void generateResultSpreadsheet(OutputSpreadsheet outputSpreadsheet);
}
