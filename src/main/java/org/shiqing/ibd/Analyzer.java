package org.shiqing.ibd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.shiqing.ibd.model.Stock;
import org.shiqing.ibd.model.StockAnalyzeResult;
import org.shiqing.ibd.model.StockList;
import org.shiqing.ibd.strategy.RatingStrategy;
import org.shiqing.ibd.strategy.Strategy;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Analyzer {

	private static final String rootDirectory = "/Users/Rossi/Documents/IBD/";
	private static List<String> spreadsheets = Lists.newArrayList();
	
	public Map<String, StockAnalyzeResult> analyze() {
		List<StockList> stockLists = Lists.newArrayList();
		getAllSpreadsheets();
		
		// Extract and formalize the necessary data
		for (String spreadsheet : spreadsheets) {
			Strategy strategy = new RatingStrategy();
			stockLists.add(strategy.extract(spreadsheet));
		}
		
		Map<String, StockAnalyzeResult> result = Maps.newHashMap();
		
		for (StockList stockList : stockLists) {
			for (Stock stock : stockList.getStocks()) {
				String symbol = stock.getSymbol();
				// If exists, update occurance and involvedSpreadsheets
				if (result.containsKey(symbol)) {
					result.get(symbol).setOccurance(result.get(symbol).getOccurance() + 1);
					result.get(symbol).getInvolvedSpreadsheets().add(stockList.getName());
				} else { 
					StockAnalyzeResult stockAnalyzeResult = new StockAnalyzeResult(
							symbol, stock.getCompanyName(), 1, Lists.newArrayList(stockList.getName()));
					result.put(symbol, stockAnalyzeResult);
				}
			}
		}
		
		return result;
	}
	
	private void getAllSpreadsheets() {
		File root = new File(rootDirectory);
		File[] files = root.listFiles();
		
		for (File file : files) {
			if (file.isFile() && !file.getName().equals("result.xls") && file.getName().endsWith(".xls")) {
				spreadsheets.add(rootDirectory + file.getName());
			}
		}
	}
	
	private void printOutResult(Map<String, StockAnalyzeResult> result) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Result");
		
		Set<String> keyset = result.keySet();
		int rownum = 0;
		for (String key : keyset) {
			Row row = sheet.createRow(rownum++);
			StockAnalyzeResult stockAnalyzeResult = result.get(key);
			int cellnum = 0;
			
			Cell symbolCell = row.createCell(cellnum++);
			Cell nameCell = row.createCell(cellnum++);
			Cell occuranceCell = row.createCell(cellnum++);
			Cell involvedSpreadsheetsCell = row.createCell(cellnum++);
			
			symbolCell.setCellValue(stockAnalyzeResult.getSymbol());
			nameCell.setCellValue(stockAnalyzeResult.getName());
			occuranceCell.setCellValue(stockAnalyzeResult.getOccurance());
			involvedSpreadsheetsCell.setCellValue(stockAnalyzeResult.getInvolvedSpreadsheets().toString());
		}
		
		try {
			FileOutputStream out = 
					new FileOutputStream(new File(rootDirectory + "result.xls"));
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
	
	public static void main(String[] args) throws IOException {
		Analyzer analyzer = new Analyzer();
		analyzer.printOutResult(analyzer.analyze());
	}

}
