package org.shiqing.ibd.scanner;

import org.shiqing.ibd.model.InputSpreadsheet;
import org.shiqing.ibd.model.input.StockList;

/**
 * 
 * @author shiqing
 * Interface of any spreadsheet scanner to implement to define what kind of data to extract from the data spreadsheet,
 * which could be a raw spreadsheet from IBD or a generated analyze result spreadsheet.
 *
 */
public interface SpreadsheetScanner {
	
	/**
	 * Extract specific data from the spreadsheet based on the criteria.
	 * @return A {@link StockList} to represent this spreadsheet
	 */
	public InputSpreadsheet extract(String filePath);
}
