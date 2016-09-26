package org.shiqing.ibd.filter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.shiqing.ibd.model.OutputSpreadsheet;
import org.shiqing.ibd.model.output.StockAnalyzeResult;
import org.shiqing.ibd.model.output.StockListAnalyzeResult;

import com.google.common.collect.Sets;

/**
 * 
 * Basic filter class focus on using symbol to filter out.
 * If you want use a certain spreadsheet symbol as the filter criteria.
 * Create YourSpecificSpreadsheetSymbolFilter.java and extend this class.
 * 
 * Override getFilteringCriteriaFile() to provide the spreadsheet name which 
 * you will its symbol list to do filtering.
 * 
 * Example : TODO Add name here
 * 
 * @author shiqing
 *
 */
public abstract class SymbolFilter implements Filter {
	
	/**
	 * Go through each row and if the symbol not showing in the filtering spreadsheet, remove the line
	 */
	public OutputSpreadsheet filtrate(OutputSpreadsheet outputSpreadsheet) {
		// TODO Better way to config this hardcode cast
		//      This may bring some issue if we want to do the filtering on the second-level result
		//      which means the outputSpreadsheet here is not necessarily an StockListAnalyzeResult instance
		//      Right now, all the filtering happen on the first-level result
		StockListAnalyzeResult result = (StockListAnalyzeResult)outputSpreadsheet;
		Set<String> filteringCriteria = getFilteringCriteria();
		
		if (filteringCriteria.isEmpty()) return outputSpreadsheet;
		
		// Need to use iterator to change map during iteration
		Iterator<Entry<String, StockAnalyzeResult>> iterator = result.getResult().entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, StockAnalyzeResult> entry = iterator.next();
			if (!filteringCriteria.contains(entry.getKey())) {
				iterator.remove();
			}
		}
		
		return result;
	}

	/**
	 * Override this method to return spreadsheet full path name that you want
	 * use as the filter spreadsheet.
	 * @return
	 */
	protected abstract String getFilteringCriteriaFile();
	
	/**
	 * Get a set of stock symbols in the filtering spreadsheet
	 * @return set of stock symbols
	 */
	private Set<String> getFilteringCriteria() {
		Set<String> filteringCriteria = Sets.newHashSet();
		
		try {
			String path = getFilteringCriteriaFile();
			FileInputStream file = new FileInputStream(path);
			
			// Get the workbook instance for the XLS file
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			
			// Get the first sheet
			HSSFSheet sheet = workbook.getSheetAt(0);
			
			// First find the row number that stock symbol starting to show
			int i = 0;
			for (; i < sheet.getPhysicalNumberOfRows(); i++) {
				Row row = sheet.getRow(i);
				if (row == null || row.getCell(0) == null || row.getCell(0).getStringCellValue() == null) {
					continue;
				} else {
					if (row.getCell(0).getStringCellValue().equalsIgnoreCase("Symbol")) {
						i++;
						break;
					}
				}
			}
			
			// Right now i has the correct line number pointing to the first symbol in the spreadsheet (if not change this should be line 10 and i=9)
			for (; i < sheet.getPhysicalNumberOfRows(); i++) {
				Row row = sheet.getRow(i);
				if (row == null || row.getCell(0) == null || row.getCell(0).getStringCellValue() == null || row.getCell(0).getStringCellValue().isEmpty()) {
					break;
				} else {
					filteringCriteria.add(row.getCell(0).getStringCellValue());
				}
			}
			
			file.close();
			workbook.close();
			
			return filteringCriteria;
		} catch (IOException e) {
			return filteringCriteria;
		}
	}	
}
