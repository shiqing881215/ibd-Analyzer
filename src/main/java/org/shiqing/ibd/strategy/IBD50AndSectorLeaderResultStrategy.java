package org.shiqing.ibd.strategy;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.shiqing.ibd.model.Spreadsheet;
import org.shiqing.ibd.model.raw.StockAnalyzeResult;
import org.shiqing.ibd.model.result.ResultStockList;

import com.google.common.collect.Sets;

/**
 * Strategy to decide how to extract the data from MM_dd_yy_ibd50_plus_sector_leader.xls
 * 
 * @author shiqing
 *
 */
public class IBD50AndSectorLeaderResultStrategy implements Strategy {

	public Spreadsheet extract(String filePath) {
		ResultStockList spreadsheet = new ResultStockList();
		
		try {
			FileInputStream file = new FileInputStream(filePath);
			
			// Get the workbook instance for the XLS file
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			
			// Get the first sheet
			HSSFSheet sheet = workbook.getSheetAt(0);
			
			// Get all the rows of the current sheet
			Iterator<Row> rowIterator = sheet.iterator();
			
			// Set spreadsheet name
			spreadsheet.setName(filePath.substring(filePath.lastIndexOf("/")+1));
			
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				
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
		IBD50AndSectorLeaderResultStrategy i = new IBD50AndSectorLeaderResultStrategy();
		i.extract("/Users/Rossi/Documents/IBD/results/09_10_16_ibd50_plus_sector_leader.xls");
	}
}
