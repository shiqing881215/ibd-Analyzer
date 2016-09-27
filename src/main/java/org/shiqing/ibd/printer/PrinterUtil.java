package org.shiqing.ibd.printer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.shiqing.ibd.analyzer.FullAnalyzer;
import org.shiqing.ibd.config.ConfigFactory;
import org.shiqing.ibd.context.Context;
import org.shiqing.ibd.model.OutputSpreadsheet;
import org.shiqing.ibd.model.output.StockAnalyzeResult;
import org.shiqing.ibd.model.output.StockListAnalyzeResult;

import com.google.common.collect.Lists;

public class PrinterUtil {
	
	// The reason to have this static variable is 
	// The print date is based on last week result
	// But all the output spreadsheets are in the same directory
	// So if we are not reusing the date in this run 
	// All the output spreadsheets generated in this run will have different date
	// which is next Friday of latest date in the result directory
	private static String printDate = null;
	
	public static void updateContext(String className) {
		Context context = ConfigFactory.get().getContextProvider().getContext();
		context.setPrinterName(className);
		ConfigFactory.get().getContextProvider().establishOrUpdateContext(context);
	}
	
	/**
	 * Generate the result spreadsheet
	 * 
	 * A sample generated spreadsheet :
	 * 
	 * GIMO	 | Gigamon Inc	            | 4	| [IBD 50, RISING PROFIT ESTIMATES, SECTOR LEADERS, STOCK SPOTLIGHT]
     * SIMO	 | SILICON MOTION TECH ADS	| 6	| [GLOBAL LEADERS, IBD 50, RISING PROFIT ESTIMATES, SECTOR LEADERS, STOCK SPOTLIGHT, STOCKS THAT FUNDS ARE BUYING]
	 * 
	 * 
	 * @param outputSpreadsheet - spreadsheet containing all the data needs to print out
	 * @param highlight - whether to highlight certain field. This is used in {@link FullAnalyzer}
	 * @param generatedFileName
	 */
	public static void generateResultSpreadsheet(OutputSpreadsheet outputSpreadsheet, 
			boolean highlight, String generatedFileName) {
		// Make sure outputSpreadsheet is an StockListAnalyzeResult instance
		// Right now Full / IBD50AndSectorLeader / HighOccurrence analyzer are using this method
		assert outputSpreadsheet instanceof StockListAnalyzeResult : "OutputSpreadsheet passed here is not a StockListAnalyzeResult instance.";
		
		StockListAnalyzeResult stockListAnalyzeResult = (StockListAnalyzeResult)outputSpreadsheet;
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Result");
		List<String> titles = getOutputSpreadsheetTitle(StockAnalyzeResult.class.getName());
		
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
			StockAnalyzeResult stockAnalyzeResult = stockListAnalyzeResult.getResult().get(key);
			int cellnum = 0;
			
			Cell symbolCell = row.createCell(cellnum++);
			Cell nameCell = row.createCell(cellnum++);
			Cell occuranceCell = row.createCell(cellnum++);
			Cell involvedSpreadsheetsCell = row.createCell(cellnum++);
			
			symbolCell.setCellValue(stockAnalyzeResult.getSymbol());
			nameCell.setCellValue(stockAnalyzeResult.getName());
			occuranceCell.setCellValue(stockAnalyzeResult.getOccurrence());
			if (stockAnalyzeResult.getOccurrence() >= 3 && highlight) {
				CellStyle style = workbook.createCellStyle();
				style.setFillBackgroundColor(IndexedColors.RED.getIndex());
				style.setFillPattern(CellStyle.ALIGN_FILL);
				occuranceCell.setCellStyle(style);
			}
			involvedSpreadsheetsCell.setCellValue(stockAnalyzeResult.getInvolvedSpreadsheets().toString());
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
	
	/**
	 * Get the print out date for all the result spreadsheet.
	 * The logic is : 
	 * Scan the result category and find the latest date result, which should be a Friday. (If not, it doesn't hurt)
	 * Return the next Friday after that date.
	 * 
	 * e.g 
	 * Latest date in the result is 09_09_16.xls
	 * The output should be 09_16_16
	 * 
	 * @return Next Friday date in format MM_dd_yy
	 */
	public static String getPrintDate() {
		if (printDate != null) {
			return printDate;
		}
		
		String latestDate = null;
		File root = new File((String)ConfigFactory.get().getPropertiesProvider().getValue("path.result"));
		File[] files = root.listFiles();
		
		// latestData in format as MM_dd_yy
		for (File file : files) {
			String fileName = file.getName();
			if (file.isFile() && fileName.length() == 12) {
				if (latestDate == null) {
					latestDate = fileName.substring(0, fileName.indexOf("."));
				} else if (latestDate.compareTo(fileName.substring(0, fileName.indexOf("."))) < 0){
					latestDate = fileName.substring(0, fileName.indexOf("."));
				}
			}
		}
		
		LocalDate nextFriday = getNextFriday(LocalDate.of(
				Integer.parseInt(latestDate.substring(latestDate.lastIndexOf("_")+1)),  // year
				Integer.parseInt(latestDate.substring(0, latestDate.indexOf("_"))),  // month 
				Integer.parseInt(latestDate.substring(latestDate.indexOf("_")+1, latestDate.lastIndexOf("_")))));  // day
		
		printDate = nextFriday.getMonthValue() < 10 ? 
				"0" + nextFriday.getMonthValue() + "_" + nextFriday.getDayOfMonth() + "_" + nextFriday.getYear() :
				nextFriday.getMonthValue() + "_" + nextFriday.getDayOfMonth() + "_" + nextFriday.getYear();
				
		return printDate;
	}
	
	public static List<String> getOutputSpreadsheetTitle(String className) {
		List<String> titles = Lists.newArrayList();
		try {
			for (Field field : Class.forName(className).getDeclaredFields()) {
				titles.add(field.toString().substring(field.toString().lastIndexOf(".")+1));
			}
		} catch (Exception e) {
			// log
			return titles;
		}
		return titles;
	}
	
	private static LocalDate getNextFriday(LocalDate d) {
		return d.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
	}
}