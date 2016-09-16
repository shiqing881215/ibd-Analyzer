package org.shiqing.ibd.printer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.shiqing.ibd.model.OutputSpreadsheet;

/**
 * Printer for IBD50 + Sector Leader analyze result.
 * 
 * @author shiqing
 *
 */
public class IBD50AndSectorLeaderSpreadsheetPrinter implements SpreadsheetPrinter {

	public void generateResultSpreadsheet(OutputSpreadsheet outputSpreadsheet) {
		DateFormat df = new SimpleDateFormat("MM_dd_yy");
		String fileName = ROOT_DIRECTORY + RESULT_DIRECTORY + 
				df.format(new Date()) + "_ibd50_plus_sector_leader" + ".xls";
		
		SpreadsheetPrinterUtil.generateResultSpreadsheet(outputSpreadsheet, false, fileName);
	}

}
