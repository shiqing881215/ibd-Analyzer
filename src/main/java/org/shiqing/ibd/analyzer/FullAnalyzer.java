package org.shiqing.ibd.analyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.shiqing.ibd.model.Stock;
import org.shiqing.ibd.model.StockAnalyzeResult;
import org.shiqing.ibd.model.StockList;
import org.shiqing.ibd.model.StockListAnalyzeResult;

/**
 * Full analyzer 
 *     - print all the info of each stock shown in the all stock lists
 *     - highlights the stocks that have equal or more than 3 in occurrence
 * 
 * @author shiqing
 *
 */
public class FullAnalyzer implements Analyzer {

	public StockListAnalyzeResult analyze(List<StockList> stockLists) {
		StockListAnalyzeResult result = new StockListAnalyzeResult();
		
		for (StockList stockList : stockLists) {
			for (Stock stock : stockList.getStocks()) {
				result.addStockAnalyzeResult(stock, stockList.getName());
			}
		}
		
		return result;
	}

	public void generateResultSpreadsheet(StockListAnalyzeResult result) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Result");
		
		Set<String> keyset = result.getResult().keySet();
		int rownum = 0;
		for (String key : keyset) {
			Row row = sheet.createRow(rownum++);
			StockAnalyzeResult stockAnalyzeResult = result.getResult().get(key);
			int cellnum = 0;
			
			Cell symbolCell = row.createCell(cellnum++);
			Cell nameCell = row.createCell(cellnum++);
			Cell occuranceCell = row.createCell(cellnum++);
			Cell involvedSpreadsheetsCell = row.createCell(cellnum++);
			
			symbolCell.setCellValue(stockAnalyzeResult.getSymbol());
			nameCell.setCellValue(stockAnalyzeResult.getName());
			occuranceCell.setCellValue(stockAnalyzeResult.getOccurrence());
			if (stockAnalyzeResult.getOccurrence() >= 3) {
				CellStyle style = workbook.createCellStyle();
				style.setFillBackgroundColor(IndexedColors.RED.getIndex());
				style.setFillPattern(CellStyle.ALIGN_FILL);
				occuranceCell.setCellStyle(style);
			}
			involvedSpreadsheetsCell.setCellValue(stockAnalyzeResult.getInvolvedSpreadsheets().toString());
		}
		
		try {
			DateFormat df = new SimpleDateFormat("MM_dd_yy");
			String fileName = df.format(new Date());
			FileOutputStream out = 
					new FileOutputStream(new File(ROOT_DIRECTORY + RESULT_DIRECTORY + fileName + ".xls"));
			workbook.write(out);
			out.close();
			workbook.close();
			System.out.println("Excel written successfully..");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
