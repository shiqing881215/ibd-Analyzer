package org.shiqing.ibd.scanner;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.shiqing.ibd.model.InputSpreadsheet;
import org.shiqing.ibd.model.input.IBD50AndSectorLeaderStockList;
import org.shiqing.ibd.model.output.StockAnalyzeResult;

import com.google.common.collect.Sets;

/**
 * Strategy to decide how to extract the data from MM_dd_yy_ibd50_plus_sector_leader.xls
 * 
 * @author shiqing
 *
 */
public class IBD50AndSectorLeaderResultScanner implements SpreadsheetScanner {

	public InputSpreadsheet extract(String filePath) {
		IBD50AndSectorLeaderStockList spreadsheet = new IBD50AndSectorLeaderStockList();
		
		try {
			FileInputStream file = new FileInputStream(filePath);
			
			// Get the workbook instance for the XLS file
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			
			// Get the first sheet
			HSSFSheet sheet = workbook.getSheetAt(0);
			
			// Get all the rows of the current sheet
			Iterator<Row> rowIterator = sheet.iterator();
			
			// Set spreadsheet name, sample spreadsheet name : 09_10_16_ibd50_plus_sector_leader.xls
			// we only want 09/10/16
			spreadsheet.setName(filePath.substring(filePath.lastIndexOf("/")+1, filePath.lastIndexOf("/")+9).replace("_", "/"));
			
			// Skip the first row which is the title
			rowIterator.next();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				
				// Defend check to prevent empty row
				if (row.getCell(0).getStringCellValue() == null || row.getCell(0).getStringCellValue().isEmpty()) {
					break;
				}
				
				StockAnalyzeResult stockAnalyzeResult = new StockAnalyzeResult(
						row.getCell(0).getStringCellValue(), 
						row.getCell(1).getStringCellValue(), 
						new Double(row.getCell(2).getNumericCellValue()).intValue(), 
						getInvolvedSet(row.getCell(3).getStringCellValue()));
				
				spreadsheet.addStock(stockAnalyzeResult);
			}
			
			file.close();
			workbook.close();
		
		} catch (IOException e) {
			return null;
		}
		
		return spreadsheet;
	}

	// An example input here is [STOCK SPOTLIGHT, IBD 50, RISING PROFIT ESTIMATES, SECTOR LEADERS]
	private Set<String> getInvolvedSet(String involveStr) {
		Set<String> involveSet = Sets.newHashSet();
		
		for (String involve : involveStr.substring(1, involveStr.length()-1).split(",")) {
			int startIndex = 0, endIndex = involve.length()-1;
			while (startIndex <= endIndex) {
				boolean change = false;
				if (involve.charAt(startIndex) == ' ' || involve.charAt(startIndex) == ',') {
					startIndex++;
					change = true;
				}
				if (involve.charAt(endIndex) == ' ' || involve.charAt(endIndex) == ',') {
					endIndex--;
					change = true;
				}
				if (!change) break;
			}
			involveSet.add(involve.substring(startIndex, endIndex+1));
		}
		
		return involveSet;
	}
	
	public static void main(String[] args) {
		IBD50AndSectorLeaderResultScanner i = new IBD50AndSectorLeaderResultScanner();
		i.extract("/Users/Rossi/Documents/IBD/results/09_10_16_ibd50_plus_sector_leader.xls");
	}
}
