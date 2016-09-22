package org.shiqing.ibd.printer;

import org.shiqing.ibd.config.ConfigFactory;
import org.shiqing.ibd.model.OutputSpreadsheet;

/**
 * Printer for IBD50 + Sector Leader history analyze result.
 * 
 * @author shiqing
 *
 */
public class IBD50AndSectorLeaderHistorySpreadsheetPrinter implements SpreadsheetPrinter {

	public void generateResultSpreadsheet(OutputSpreadsheet outputSpreadsheet) {
		String fileName = (String)ConfigFactory.get().getPropertiesProvider().getValue("path.result") + "Golden.xls";
		
		SpreadsheetPrinterUtil.generateGoldenSpreadsheet(outputSpreadsheet, fileName);
	}

}
