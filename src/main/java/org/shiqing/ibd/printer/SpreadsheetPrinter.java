package org.shiqing.ibd.printer;

import org.shiqing.ibd.model.OutputSpreadsheet;

/**
 * Interface defining generating the final result spreadsheet.
 * 
 * @author shiqing
 *
 */
public interface SpreadsheetPrinter {
	
	public static final String ROOT_DIRECTORY = "/Users/Rossi/Documents/IBD/";
	public static final String RESULT_DIRECTORY = "results/"; 
	
	/**
	 * Generate a spreadsheet that contains final the analyze result
	 * @param stockListAnalyzeResult
	 */
	public void generateResultSpreadsheet(OutputSpreadsheet outputSpreadsheet);
}
