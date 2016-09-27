package org.shiqing.ibd.goldfile;

import org.shiqing.ibd.model.OutputSpreadsheet;
import org.shiqing.ibd.printer.HighOccurrenceSpreadsheetPrinter;
import org.shiqing.ibd.printer.PrinterUtil;

/**
 * 
 * Test implementation of {@link HighOccurrenceSpreadsheetPrinter}.
 * Major purpose here is to override the output file directory.
 * 
 * @author shiqing
 *
 */
public class HighOccurrenceSpreadsheetTestPrinter extends HighOccurrenceSpreadsheetPrinter {

	@Override
	public void generateResultSpreadsheet(OutputSpreadsheet outputSpreadsheet) {
		String fileName = "/Users/Rossi/Documents/workspace/ibd/src/test/java/org/shiqing/ibd/goldfile/raw/results/high_occurrence.xls";
		
		PrinterUtil.generateResultSpreadsheet(outputSpreadsheet, false, fileName);
	}
	
}
