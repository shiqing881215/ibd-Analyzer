package org.shiqing.ibd.printer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.shiqing.ibd.config.ConfigFactory;
import org.shiqing.ibd.model.OutputSpreadsheet;
import org.shiqing.ibd.model.output.IBD50AndSectorLeaderStockAnalyzeResult;
import org.shiqing.ibd.model.output.IBD50AndSectorLeaderStockListAnalyzeResult;

/**
 * Printer for IBD50 + Sector Leader history analyze result.
 * 
 * @author shiqing
 *
 */
public class IBD50AndSectorLeaderHistorySpreadsheetPrinter implements SpreadsheetPrinter {

	public void generateResultSpreadsheet(OutputSpreadsheet outputSpreadsheet) {
		String fileName = (String)ConfigFactory.get().getPropertiesProvider().getValue("path.result") + "Golden.xls";
		
		generateGoldenSpreadsheet(outputSpreadsheet, fileName);
	}
	
	private void generateGoldenSpreadsheet(OutputSpreadsheet outputSpreadsheet, String generatedFileName) {
		// Make sure outputSpreadsheet is an IBD50AndSectorLeaderStockListAnalyzeResult instance
		// Right now IBD50AndSectorLeaderHistory analyzer is using this method
		assert outputSpreadsheet instanceof IBD50AndSectorLeaderStockListAnalyzeResult : "OutputSpreadsheet passed here is not a IBD50AndSectorLeaderStockListAnalyzeResult instance.";
		
		IBD50AndSectorLeaderStockListAnalyzeResult stockListAnalyzeResult = (IBD50AndSectorLeaderStockListAnalyzeResult)outputSpreadsheet;
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Result");
		List<String> titles = SpreadsheetPrinterUtil.getOutputSpreadsheetTitle(IBD50AndSectorLeaderStockAnalyzeResult.class.getName());
		
		Set<String> keyset = stockListAnalyzeResult.getResult().keySet();
		int rownum = 0;
		// Print title first
		Row row = sheet.createRow(rownum++);
		int cellNum = 0;
		for (String title : titles) {
			row.createCell(cellNum++).setCellValue(title);
		}
		
		for (String key : keyset) {
			row = sheet.createRow(rownum++);
			IBD50AndSectorLeaderStockAnalyzeResult stockAnalyzeResult = stockListAnalyzeResult.getResult().get(key);
			int cellnum = 0;
			
			Cell symbolCell = row.createCell(cellnum++);
			Cell nameCell = row.createCell(cellnum++);
			Cell occuranceCell = row.createCell(cellnum++);
			Cell involvedSpreadsheetsCell = row.createCell(cellnum++);
			Cell continuityCell = row.createCell(cellnum++);
			Cell oneWeekPerformanceCell = row.createCell(cellnum++);
			Cell oneMonthPerformanceCell = row.createCell(cellnum++);
			Cell threeMonthsPerformanceCell = row.createCell(cellnum++);
			Cell sixMonthsPerformanceCell = row.createCell(cellnum++);
			
			symbolCell.setCellValue(stockAnalyzeResult.getSymbol());
			nameCell.setCellValue(stockAnalyzeResult.getName());
			occuranceCell.setCellValue(stockAnalyzeResult.getOccurrence());
			// Sort dates before printing out
			Object[] sortedDates = stockAnalyzeResult.getInvolvedDates().toArray();
			Arrays.sort(sortedDates);
			involvedSpreadsheetsCell.setCellValue(Arrays.toString(sortedDates));
			continuityCell.setCellValue(stockAnalyzeResult.getContinuity());
			oneWeekPerformanceCell.setCellValue(MessageFormat.format("{0,number,#.##%}", stockAnalyzeResult.getOneWeekPerformance()));
			oneMonthPerformanceCell.setCellValue(MessageFormat.format("{0,number,#.##%}", stockAnalyzeResult.getOneMonthPerformance()));
			threeMonthsPerformanceCell.setCellValue(MessageFormat.format("{0,number,#.##%}", stockAnalyzeResult.getThreeMonthsPerformance()));
			sixMonthsPerformanceCell.setCellValue(MessageFormat.format("{0,number,#.##%}", stockAnalyzeResult.getSixMonthsPerformance()));
		}
		
		try {
			FileOutputStream out = 
					new FileOutputStream(new File(generatedFileName));
			workbook.write(out);
			out.close();
			workbook.close();
			System.out.println(generatedFileName + " written successfully..");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
