package org.shiqing.ibd.printer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.shiqing.ibd.model.OutputSpreadsheet;

/**
 * Printer for high occurrence analyze result.
 * 
 * @author shiqing
 *
 */
public class HighOccurrenceSpreadsheetPrinter implements SpreadsheetPrinter {

	public void generateResultSpreadsheet(OutputSpreadsheet outputSpreadsheet) {
		DateFormat df = new SimpleDateFormat("MM_dd_yy");
		String fileName = ROOT_DIRECTORY + RESULT_DIRECTORY + 
				df.format(new Date()) + "_high_occurence" + ".xls";
		
		SpreadsheetPrinterUtil.generateResultSpreadsheet(outputSpreadsheet, false, fileName);
	}

}
