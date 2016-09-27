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
		// Update the context first
		PrinterUtil.updateContext(this.getClass().getSimpleName());
		
		String fileName = (String)ConfigFactory.get().getPropertiesProvider().getValue("path.result") 
				+ PrinterUtil.getPrintDate() + ".xls";
		
		PrinterUtil.generateResultSpreadsheet(outputSpreadsheet, true, fileName);
	}

}
