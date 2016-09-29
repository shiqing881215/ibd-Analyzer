package org.shiqing.ibd.goldfile;

import org.shiqing.ibd.config.ConfigFactory;
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
		String fileName = ConfigFactory.get().getPropertiesProvider().getValue("path.test.result") + "high_occurrence.xls";
		
		PrinterUtil.generateResultSpreadsheet(outputSpreadsheet, fileName);
	}
	
}
