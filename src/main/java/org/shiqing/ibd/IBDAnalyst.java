package org.shiqing.ibd;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.shiqing.ibd.analyzer.Analyzer;
import org.shiqing.ibd.analyzer.FullAnalyzer;
import org.shiqing.ibd.analyzer.IBD50PlusSectorLeaderAnalyzer;
import org.shiqing.ibd.model.StockList;
import org.shiqing.ibd.model.StockListAnalyzeResult;
import org.shiqing.ibd.strategy.RatingStrategy;
import org.shiqing.ibd.strategy.Strategy;

import com.google.common.collect.Lists;

/**
 * Main program to run to get analyze results.
 * 
 * @author shiqing
 *
 */
public class IBDAnalyst {

	private static final String ROOT_DIRECTORY = "/Users/Rossi/Documents/IBD/";
	private static List<String> spreadsheets = Lists.newArrayList();
	
	private Analyzer assistant;
	
	public IBDAnalyst(Analyzer assistant) {
		this.assistant = assistant;
	}
	
	public StockListAnalyzeResult analyze() {
		List<StockList> stockLists = Lists.newArrayList();
		getAllSpreadsheets();
		
		// Extract and formalize the necessary data
		for (String spreadsheet : spreadsheets) {
			Strategy strategy = new RatingStrategy();
			stockLists.add(strategy.extract(spreadsheet));
		}
		
		return assistant.analyze(stockLists);
	}
	
	private void getAllSpreadsheets() {
		File root = new File(ROOT_DIRECTORY);
		File[] files = root.listFiles();
		
		for (File file : files) {
			if (file.isFile() && !file.getName().equals("result.xls") && file.getName().endsWith(".xls")) {
				spreadsheets.add(ROOT_DIRECTORY + file.getName());
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		IBDAnalyst analyst = new IBDAnalyst(new FullAnalyzer());
		analyst.assistant.generateResultSpreadsheet(analyst.analyze());
		
		analyst = new IBDAnalyst(new IBD50PlusSectorLeaderAnalyzer());
		analyst.assistant.generateResultSpreadsheet(analyst.analyze());
	}
}