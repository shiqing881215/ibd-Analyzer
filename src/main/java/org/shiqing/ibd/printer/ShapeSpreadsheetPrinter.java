package org.shiqing.ibd.printer;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.shiqing.ibd.config.ConfigFactory;
import org.shiqing.ibd.model.OutputSpreadsheet;

/**
 * Printer for shape analyst
 * 
 * @author shiqing
 *
 */
public class ShapeSpreadsheetPrinter implements SpreadsheetPrinter {

	public void generateResultSpreadsheet(OutputSpreadsheet outputSpreadsheet) {
		// Update the context first
		PrinterUtil.updateContext(this.getClass().getSimpleName());
		
		// Just print current date
		String fileName = (String)ConfigFactory.get().getPropertiesProvider().getValue("path.result") 
				+ new SimpleDateFormat("MM_dd_yy").format(new Date()) + "_shape.xls";
		
		PrinterUtil.generateResultSpreadsheet(outputSpreadsheet, fileName);
	}

}
