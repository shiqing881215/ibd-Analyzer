package org.shiqing.ibd;

import java.io.IOException;
import java.util.List;

import org.shiqing.ibd.analyzer.Analyzer;
import org.shiqing.ibd.analyzer.FullAnalyzer;
import org.shiqing.ibd.analyzer.HighOccurrenceAnalyzer;
import org.shiqing.ibd.analyzer.IBD50PlusSectorLeaderAnalyzer;
import org.shiqing.ibd.model.Spreadsheet;
import org.shiqing.ibd.model.output.StockListAnalyzeResult;
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

	private Analyzer assistant;
	
	public IBDAnalyst(Analyzer assistant) {
		this.assistant = assistant;
	}
	
	public StockListAnalyzeResult analyze() {
		List<Spreadsheet> stockLists = Lists.newArrayList();
		
		// Extract and formalize the necessary data
		for (String spreadsheet : AnalystUtil.getAllSpreadsheets()) {
			Strategy strategy = new RatingStrategy();
			stockLists.add(strategy.extract(spreadsheet));
		}
		
		return assistant.analyze(stockLists);
	}
	
	public static void main(String[] args) throws IOException {
		IBDAnalyst analyst = new IBDAnalyst(new FullAnalyzer());
		analyst.assistant.generateResultSpreadsheet(analyst.analyze());
		
		analyst = new IBDAnalyst(new IBD50PlusSectorLeaderAnalyzer());
		analyst.assistant.generateResultSpreadsheet(analyst.analyze());
		
		analyst = new IBDAnalyst(new HighOccurrenceAnalyzer());
		analyst.assistant.generateResultSpreadsheet(analyst.analyze());
	}
}