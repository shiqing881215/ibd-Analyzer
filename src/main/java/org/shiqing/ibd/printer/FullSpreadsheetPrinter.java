package org.shiqing.ibd.printer;

import org.shiqing.ibd.config.ConfigFactory;
import org.shiqing.ibd.model.OutputSpreadsheet;

/**
 * Printer for full analyze result.
 * 
 * @author shiqing
 *
 */
public class FullSpreadsheetPrinter implements SpreadsheetPrinter {

	public void generateResultSpreadsheet(OutputSpreadsheet outputSpreadsheet) {
		String fileName = (String)ConfigFactory.get().getPropertiesProvider().getValue("path.result") 
				+ SpreadsheetPrinterUtil.getPrintDate() + ".xls";
		
		SpreadsheetPrinterUtil.generateResultSpreadsheet(outputSpreadsheet, true, fileName);
	}

}
