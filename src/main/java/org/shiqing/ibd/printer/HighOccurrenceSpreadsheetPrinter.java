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
		String fileName = (String)ConfigFactory.get().getPropertiesProvider().getValue("path.result") + 
				SpreadsheetPrinterUtil.getPrintDate() + "_high_occurence" + ".xls";
		
		SpreadsheetPrinterUtil.generateResultSpreadsheet(outputSpreadsheet, false, fileName);
	}

}
