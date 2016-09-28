package org.shiqing.ibd.scanner;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.shiqing.ibd.model.InputSpreadsheet;
import org.shiqing.ibd.model.input.Stock;
import org.shiqing.ibd.model.input.StockList;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Scanner that is focused on extracting the rating data.
 * 
 * @author shiqing
 *
 */
public class RatingScanner implements SpreadsheetScanner {

	// TODO Remove this hardcode string list
	private static final List<String> RATING_CATEGORY = Lists.newArrayList(
			"Symbol", "Company Name", "Price", "Composite Rating", "EPS Rating", "RS Rating", "SMR Rating", "ACC/DIS Rating");
	private Map<String, Integer> ratingCategoryColumnIndex = Maps.newHashMap();
	
	public InputSpreadsheet extract(String filePath) {
		// First update the context
		ScannerUtil.updateContext(this.getClass().getSimpleName());
		
		StockList stockList = new StockList();
		
		try {
			FileInputStream file = new FileInputStream(filePath);
			
			// Get the workbook instance for the XLS file
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			
			// Get the first sheet
			HSSFSheet sheet = workbook.getSheetAt(0);
			
			// Get all the rows of the current sheet
			Iterator<Row> rowIterator = sheet.iterator();
			
			// Attention : must call these two methods in this order
			setStockListName(rowIterator, stockList);
			searchRatingCategoryColumnIndex(rowIterator);
			
			// Right now iterator starts at the next row of the header row 
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				if (row.getCell(0).getStringCellValue().isEmpty()) {
					break;
				}
				Stock stock = new Stock(
						row.getCell(ratingCategoryColumnIndex.get(RATING_CATEGORY.get(0))).getStringCellValue(),  // Symbol
						row.getCell(ratingCategoryColumnIndex.get(RATING_CATEGORY.get(1))).getStringCellValue(), // companyName
						Double.parseDouble(row.getCell(ratingCategoryColumnIndex.get(RATING_CATEGORY.get(2))).getStringCellValue()), // price
						row.getCell(ratingCategoryColumnIndex.get(RATING_CATEGORY.get(3))).getStringCellValue().isEmpty() ? 
								0 : Integer.parseInt(row.getCell(ratingCategoryColumnIndex.get(RATING_CATEGORY.get(3))).getStringCellValue()), // compositeRating
						row.getCell(ratingCategoryColumnIndex.get(RATING_CATEGORY.get(4))).getStringCellValue().isEmpty() ?
								0 : Integer.parseInt(row.getCell(ratingCategoryColumnIndex.get(RATING_CATEGORY.get(4))).getStringCellValue()), // ePSRating
						row.getCell(ratingCategoryColumnIndex.get(RATING_CATEGORY.get(5))).getStringCellValue().isEmpty() ?
								0 : Integer.parseInt(row.getCell(ratingCategoryColumnIndex.get(RATING_CATEGORY.get(5))).getStringCellValue()), // rSRating
						row.getCell(ratingCategoryColumnIndex.get(RATING_CATEGORY.get(6))).getStringCellValue(), // sMRRating
						row.getCell(ratingCategoryColumnIndex.get(RATING_CATEGORY.get(7))).getStringCellValue()); // aCC_DISRating
				
				stockList.addStock(stock);
			}
			
			file.close();
			workbook.close();
		
		} catch (IOException e) {
			return null;
		}
		
		return stockList;
	}

	/**
	 * After this call, stockList name should be set
	 * @param rowIterator
	 * @param stockList
	 */
	private void setStockListName(Iterator<Row> rowIterator, StockList stockList) {
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Cell firstCell = row.getCell(0);
			
			// Find the header row
			if (firstCell.getStringCellValue().contains("Stock List")) {
				stockList.setName(row.getCell(1).getStringCellValue());
				return;
			}
		}
	}

	/**
	 * After this call, ratingCategoryColumnIndex should be filled up
	 * @param rowIterator
	 */
	private void searchRatingCategoryColumnIndex(Iterator<Row> rowIterator) {
		// Clear it in case 
		ratingCategoryColumnIndex.clear();
		
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Cell firstCell = row.getCell(0);
			
			// Find the header row
			if (firstCell.getStringCellValue().equals("Symbol")) {
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					
					if (RATING_CATEGORY.contains(cell.getStringCellValue())) {
						// Map cannot contain this before, so just add it
						ratingCategoryColumnIndex.put(cell.getStringCellValue(), cell.getColumnIndex());
						
						// All the index has found
						if (ratingCategoryColumnIndex.size() == RATING_CATEGORY.size()) {
							break;
						}
					}
				}
				break;
			}
		}
	}
}
