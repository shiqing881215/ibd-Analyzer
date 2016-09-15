package org.shiqing.ibd.analyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.shiqing.ibd.model.output.StockAnalyzeResult;
import org.shiqing.ibd.model.output.StockListAnalyzeResult;

public class AnalyzerUtil {
	
	/**
	 * Generate the result spreadsheet
	 * @param stockListAnalyzeResult
	 * @param highlight - whether to highlight certain field. This is used in {@link FullAnalyzer}
	 * @param generatedFileName
	 */
	public static void generateResultSpreadsheet(StockListAnalyzeResult stockListAnalyzeResult, 
			boolean highlight, String generatedFileName) {
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
}
