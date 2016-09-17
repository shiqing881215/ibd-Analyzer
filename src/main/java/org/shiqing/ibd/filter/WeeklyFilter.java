package org.shiqing.ibd.filter;

import java.io.File;
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
 * A weekly filter which uses YOUR WEEKLY REVIVEW.xls as the filtering criteria.
 * 
 * The reason to have this filter is :
 * 1. Right now the IBD50 + Sector Leader is only based on the raw spreadsheets from IBD on each Friday,
 *    even through the spreadsheet is updated each day. (Lack of automation to do this. TODO improve it)
 * 2. So there could be two situation which is not ideal : 
 *    1) A stock showing up from Monday to Thursday but miss Friday. But this is fine, we only care about
 *       the stock which is still showing on the list by the end of each week. So if it disappear from Friday,
 *       then that means it's not good and we don't want it any more.
 *    2) A stock NOT showing from Monday to Thursday but only showing on Friday. We want to find a true leader
 *       instead of some magical stock just showing up once. So to avoid this situation, we use the weekly review
 *       spreadsheet as a defender which list the stocks performing good across the week. 
 *       So if a stock is just a flash in the pan, it will be filtered by this.
 *       
 *  Also, ideally as mentioned above. If finally we can make this program running daily, this is not necessary any more.
 * 
 * @author Rossi
 *
 */
public class WeeklyFilter implements Filter {
	
	private static final String ROOT_DIRECTORY = "/Users/Rossi/Documents/IBD/";

	/**
	 * Go through each row and if the symbol not showing in the weekly review, remove the line
	 */
	public OutputSpreadsheet filtrate(OutputSpreadsheet outputSpreadsheet) {
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

	private String getFilteringCriteriaFile() {
		File root = new File(ROOT_DIRECTORY);
		File[] files = root.listFiles();
		
		// TODO Remove hardcode directory
		for (File file : files) {
			if (file.isFile() && file.getName().contains("WEEKLY")) {
				return ROOT_DIRECTORY + file.getName();
			}
		}
		
		return null;
	}
	
	private Set<String> getFilteringCriteria() {
		Set<String> filteringCriteria = Sets.newHashSet();
		
		try {
			String path = getFilteringCriteriaFile();
			FileInputStream file = new FileInputStream(path);
			
			// Get the workbook instance for the XLS file
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			
			// Get the first sheet
			HSSFSheet sheet = workbook.getSheetAt(0);
			
			// Start from line 10. TODO better way for this hardcode line number
			for (int i = 9; i < sheet.getPhysicalNumberOfRows(); i++) {
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