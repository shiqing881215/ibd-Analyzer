package org.shiqing.ibd.printer;

import org.shiqing.ibd.config.ConfigFactory;
import org.shiqing.ibd.model.OutputSpreadsheet;

/**
 * Printer for IBD50 + Sector Leader analyze result.
 * 
 * @author shiqing
 *
 */
public class IBD50AndSectorLeaderSpreadsheetPrinter implements SpreadsheetPrinter {

	public void generateResultSpreadsheet(OutputSpreadsheet outputSpreadsheet) {
		// Update the context first
		PrinterUtil.updateContext(this.getClass().getSimpleName());
		
		String fileName = (String)ConfigFactory.get().getPropertiesProvider().getValue("path.result") + 
				PrinterUtil.getPrintDate("ibd50_plus_sector_leader") + "_ibd50_plus_sector_leader.xls";
		
		PrinterUtil.generateResultSpreadsheet(outputSpreadsheet, fileName);
	}

}
