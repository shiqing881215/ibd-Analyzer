package org.shiqing.ibd.printer;

import org.shiqing.ibd.config.ConfigFactory;
import org.shiqing.ibd.model.OutputSpreadsheet;

/**
 * 
 * Printer for accumulation / distribution analyze result.
 * 
 * @author shiqing
 *
 */
public class AccDisSpreadsheetPrinter implements SpreadsheetPrinter {

	public void generateResultSpreadsheet(OutputSpreadsheet outputSpreadsheet) {
		// Update the context first
		PrinterUtil.updateContext(this.getClass().getSimpleName());

		String fileName = (String)ConfigFactory.get().getPropertiesProvider().getValue("path.result") 
				+ PrinterUtil.getPrintDate("acc_dis") + ".xls";
		
		PrinterUtil.generateResultSpreadsheet(outputSpreadsheet, fileName);
	}

}
