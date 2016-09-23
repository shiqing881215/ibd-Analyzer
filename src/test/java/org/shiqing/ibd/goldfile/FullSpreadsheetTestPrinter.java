package org.shiqing.ibd.goldfile;

import org.shiqing.ibd.model.OutputSpreadsheet;
import org.shiqing.ibd.printer.FullSpreadsheetPrinter;
import org.shiqing.ibd.printer.SpreadsheetPrinterUtil;

/**
 * 
 * Test implementation of {@link FullSpreadsheetPrinter}.
 * Major purpose here is to override the output file directory.
 * 
 * @author shiqing
 *
 */
public class FullSpreadsheetTestPrinter extends FullSpreadsheetPrinter {

	@Override
	public void generateResultSpreadsheet(OutputSpreadsheet outputSpreadsheet) {
		String fileName = "/Users/Rossi/Documents/workspace/ibd/src/test/java/org/shiqing/ibd/goldfile/raw/results/full.xls";
		
		SpreadsheetPrinterUtil.generateResultSpreadsheet(outputSpreadsheet, true, fileName);
	}
	
}
