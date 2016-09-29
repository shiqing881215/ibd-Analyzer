package org.shiqing.ibd.goldfile;

import org.shiqing.ibd.config.ConfigFactory;
import org.shiqing.ibd.model.OutputSpreadsheet;
import org.shiqing.ibd.printer.FullSpreadsheetPrinter;
import org.shiqing.ibd.printer.PrinterUtil;

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
		String fileName = ConfigFactory.get().getPropertiesProvider().getValue("path.test.result") + "full.xls";
		
		PrinterUtil.generateResultSpreadsheet(outputSpreadsheet, fileName);
	}
	
}
