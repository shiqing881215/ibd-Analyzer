package org.shiqing.ibd.printer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.shiqing.ibd.analyzer.FullAnalyzer;
import org.shiqing.ibd.model.OutputSpreadsheet;
import org.shiqing.ibd.model.output.IBD50AndSectorLeaderStockAnalyzeResult;
import org.shiqing.ibd.model.output.IBD50AndSectorLeaderStockListAnalyzeResult;
import org.shiqing.ibd.model.output.StockAnalyzeResult;
import org.shiqing.ibd.model.output.StockListAnalyzeResult;

public class SpreadsheetPrinterUtil {
	
	public static final String ROOT_DIRECTORY = "/Users/Rossi/Documents/IBD/";
	public static final String RESULT_DIRECTORY = ROOT_DIRECTORY + "results/"; 
	
	// TODO Better way
	private static String printDate = null;
	
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
		// TODO Better way
		StockListAnalyzeResult stockListAnalyzeResult = (StockListAnalyzeResult)outputSpreadsheet;
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Result");
		
		Set<String> keyset = stockListAnalyzeResult.getResult().keySet();
		int rownum = 0;
		for (String key : keyset) {
			Row row = sheet.createRow(rownum++);
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
	
	public static void generateGoldenSpreadsheet(OutputSpreadsheet outputSpreadsheet, String generatedFileName) {
		// TODO Better way
		IBD50AndSectorLeaderStockListAnalyzeResult stockListAnalyzeResult = (IBD50AndSectorLeaderStockListAnalyzeResult)outputSpreadsheet;
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Result");
		
		Set<String> keyset = stockListAnalyzeResult.getResult().keySet();
		int rownum = 0;
		for (String key : keyset) {
			Row row = sheet.createRow(rownum++);
			IBD50AndSectorLeaderStockAnalyzeResult stockAnalyzeResult = stockListAnalyzeResult.getResult().get(key);
			int cellnum = 0;
			
			Cell symbolCell = row.createCell(cellnum++);
			Cell nameCell = row.createCell(cellnum++);
			Cell occuranceCell = row.createCell(cellnum++);
			Cell involvedSpreadsheetsCell = row.createCell(cellnum++);
			
			symbolCell.setCellValue(stockAnalyzeResult.getSymbol());
			nameCell.setCellValue(stockAnalyzeResult.getName());
			occuranceCell.setCellValue(stockAnalyzeResult.getOccurrence());
			// Sort dates before printing out
			Object[] sortedDates = stockAnalyzeResult.getInvolvedDates().toArray();
			Arrays.sort(sortedDates);
			involvedSpreadsheetsCell.setCellValue(Arrays.toString(sortedDates));
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
	
	private static LocalDate getNextFriday(LocalDate d) {
		return d.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
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
		File root = new File(RESULT_DIRECTORY);
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
	
	public static void main(String[] args) {
		System.out.println(getPrintDate());
	}
}
