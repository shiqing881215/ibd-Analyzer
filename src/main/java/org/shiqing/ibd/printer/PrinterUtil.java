package org.shiqing.ibd.printer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.shiqing.ibd.analyzer.FullAnalyzer;
import org.shiqing.ibd.analyzer.HighOccurrenceAnalyzer;
import org.shiqing.ibd.analyzer.IBD50AndSectorLeaderAnalyzer;
import org.shiqing.ibd.analyzer.IBD50AndSectorLeaderHistoryAnalyzer;
import org.shiqing.ibd.analyzer.IPOAnalyzer;
import org.shiqing.ibd.config.ConfigFactory;
import org.shiqing.ibd.context.Context;
import org.shiqing.ibd.enrich.ContinuityEnricher;
import org.shiqing.ibd.enrich.QuotePerformanceEnricher;
import org.shiqing.ibd.model.OutputSpreadsheet;
import org.shiqing.ibd.model.TimePeriod;
import org.shiqing.ibd.model.output.StockAnalyzeResult;
import org.shiqing.ibd.model.output.StockListAnalyzeResult;

import com.google.common.collect.Lists;

public class PrinterUtil {
	
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
	public static void generateResultSpreadsheet(OutputSpreadsheet outputSpreadsheet, String generatedFileName) {
		// Make sure outputSpreadsheet is an StockListAnalyzeResult instance
		// Right now Full / IBD50AndSectorLeader / HighOccurrence / IPO analyzer are using this method
		assert outputSpreadsheet instanceof StockListAnalyzeResult : "OutputSpreadsheet passed here is not a StockListAnalyzeResult instance.";
		
		StockListAnalyzeResult stockListAnalyzeResult = (StockListAnalyzeResult)outputSpreadsheet;
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Result");
		List<String> titles = getTitles();
		
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
			
			// Basic info
			Cell symbolCell = row.createCell(cellnum++);
			Cell nameCell = row.createCell(cellnum++);
			Cell occuranceCell = row.createCell(cellnum++);
			Cell involvedSpreadsheetsCell = row.createCell(cellnum++);
			
			symbolCell.setCellValue(stockAnalyzeResult.getSymbol());
			nameCell.setCellValue(stockAnalyzeResult.getName());
			occuranceCell.setCellValue(stockAnalyzeResult.getOccurrence());
			involvedSpreadsheetsCell.setCellValue(stockAnalyzeResult.getInvolvedSpreadsheets().toString());
			
			// Only highlight full list
			if (ConfigFactory.get().getContextProvider().getContext().getAnalyzerName().equals(FullAnalyzer.class.getSimpleName())) {
				if (stockAnalyzeResult.getOccurrence() >= 3) {
					CellStyle style = workbook.createCellStyle();
					style.setFillBackgroundColor(IndexedColors.RED.getIndex());
					style.setFillPattern(CellStyle.ALIGN_FILL);
					occuranceCell.setCellStyle(style);
				}
			}
			
			// Performance quotes
			if (ConfigFactory.get().getContextProvider().getContext().getEnricherNames().contains(QuotePerformanceEnricher.class.getSimpleName())) {
				Cell oneWeekPerformanceCell = row.createCell(cellnum++);
				Cell oneMonthPerformanceCell = row.createCell(cellnum++);
				Cell threeMonthsPerformanceCell = row.createCell(cellnum++);
				Cell sixMonthsPerformanceCell = row.createCell(cellnum++);
				
				oneWeekPerformanceCell.setCellValue(MessageFormat.format("{0,number,#.##%}", stockAnalyzeResult.getQuotePerformance().get(TimePeriod.ONE_WEEK)));
				oneMonthPerformanceCell.setCellValue(MessageFormat.format("{0,number,#.##%}", stockAnalyzeResult.getQuotePerformance().get(TimePeriod.ONE_MONTH)));
				threeMonthsPerformanceCell.setCellValue(MessageFormat.format("{0,number,#.##%}", stockAnalyzeResult.getQuotePerformance().get(TimePeriod.THREE_MONTHS)));
				sixMonthsPerformanceCell.setCellValue(MessageFormat.format("{0,number,#.##%}", stockAnalyzeResult.getQuotePerformance().get(TimePeriod.SIX_MONTHS)));
			}
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
	 * @param fileNameKeyWord - a key work in result name like "ipo", "high_occurence", "ibd50_plus_sector_leader". 
	 *                          For full result, it's "".
	 * 
	 * @return Next Friday date in format MM_dd_yy
	 */
	public static String getPrintDate(String fileNameKeyWord) {
		String latestDate = null;
		File root = new File((String)ConfigFactory.get().getPropertiesProvider().getValue("path.result"));
		File[] files = root.listFiles();
		
		// latestData in format as MM_dd_yy
		for (File file : files) {
			String fileName = file.getName();
			// Either it's full result which length is 12 or it contains the specific filename keyword
			if ((fileNameKeyWord.isEmpty() && fileName.length() == 12) || (!fileNameKeyWord.isEmpty() && fileName.contains(fileNameKeyWord))) {
				if (file.isFile()) {
					if (latestDate == null) {
						latestDate = fileName.substring(0, 8);
					} else if (latestDate.compareTo(fileName.substring(0, 8)) < 0){
						latestDate = fileName.substring(0, 8);
					}
				}
			}
		}
		
		// If result directory doesn't have any result yet for this analyst. Use current date
		if (latestDate == null) {
			DateFormat df = new SimpleDateFormat("MM_dd_yy");
			
			return df.format(new Date()) + "_" + fileNameKeyWord; 
		}
		
		LocalDate nextFriday = getNextFriday(LocalDate.of(
				Integer.parseInt(latestDate.substring(latestDate.lastIndexOf("_")+1)),  // year
				Integer.parseInt(latestDate.substring(0, latestDate.indexOf("_"))),  // month 
				Integer.parseInt(latestDate.substring(latestDate.indexOf("_")+1, latestDate.lastIndexOf("_")))));  // day
		
		String printMonth = nextFriday.getMonthValue() < 10 ? "0" + nextFriday.getMonthValue() : nextFriday.getMonthValue() + "";
		String printDay = nextFriday.getDayOfMonth() < 10 ? "0" + nextFriday.getDayOfMonth() : nextFriday.getDayOfMonth() + "";
		
		return printMonth + "_" + printDay + "_" + nextFriday.getYear();
	}
	
	/**
	 * Based on the context, generate the titles in the result spreadsheet.
	 * 
	 * @return titles
	 */
	public static List<String> getTitles() {
		List<String> titles = Lists.newArrayList();
		Context context = ConfigFactory.get().getContextProvider().getContext();
		
		// Default
		titles.add("Symbol");
		titles.add("Name");
		
		if (context.getAnalyzerName().equals(FullAnalyzer.class.getSimpleName()) || 
				context.getAnalyzerName().equals(HighOccurrenceAnalyzer.class.getSimpleName())||
				context.getAnalyzerName().equals(IBD50AndSectorLeaderAnalyzer.class.getSimpleName()) ||
				context.getAnalyzerName().equals(IPOAnalyzer.class.getSimpleName())) {
			titles.add("Occurrence");
			titles.add("Sheets");
		} else if (context.getAnalyzerName().equals(IBD50AndSectorLeaderHistoryAnalyzer.class.getSimpleName())) {
			titles.add("Occurrence");
			titles.add("Dates");
		}
		
		if (context.getEnricherNames().contains(ContinuityEnricher.class.getSimpleName())) {
			titles.add("Continuity");
		}
		
		if (context.getEnricherNames().contains(QuotePerformanceEnricher.class.getSimpleName())) {
			titles.add("One Week");
			titles.add("One Month");
			titles.add("Three Months");
			titles.add("Six Months");
		}
		
		return titles;
	}
	
	private static LocalDate getNextFriday(LocalDate d) {
		return d.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
	}
}