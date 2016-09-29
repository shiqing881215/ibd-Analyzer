package org.shiqing.ibd.printer;

import org.shiqing.ibd.config.ConfigFactory;
import org.shiqing.ibd.model.OutputSpreadsheet;

/**
 * Printer for high occurrence analyze result.
 * 
 * @author shiqing
 *
 */
public class HighOccurrenceSpreadsheetPrinter implements SpreadsheetPrinter {

	public void generateResultSpreadsheet(OutputSpreadsheet outputSpreadsheet) {
		// Update the context first
		PrinterUtil.updateContext(this.getClass().getSimpleName());
		
		String fileName = (String)ConfigFactory.get().getPropertiesProvider().getValue("path.result") + 
				PrinterUtil.getPrintDate("high_occurence") + "_high_occurence.xls";
		
		PrinterUtil.generateResultSpreadsheet(outputSpreadsheet, fileName);
	}

}
