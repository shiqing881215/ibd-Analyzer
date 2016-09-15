package org.shiqing.ibd;

import java.io.IOException;
import java.util.List;

import org.shiqing.ibd.analyzer.Analyzer;
import org.shiqing.ibd.analyzer.FullAnalyzer;
import org.shiqing.ibd.analyzer.HighOccurrenceAnalyzer;
import org.shiqing.ibd.analyzer.IBD50PlusSectorLeaderAnalyzer;
import org.shiqing.ibd.model.InputSpreadsheet;
import org.shiqing.ibd.model.OutputSpreadsheet;
import org.shiqing.ibd.scanner.RatingScanner;
import org.shiqing.ibd.scanner.SpreadsheetScanner;

import com.google.common.collect.Lists;

/**
 * Basic Analyst to get the basic result which include : 
 * - full results
 * - IBD50 + Sector Leader
 * - High Occurrence
 * 
 * @author shiqing
 *
 */
public class BasicAnalyst extends Analyst {

	public BasicAnalyst(Analyzer assistant) {
		super(assistant);
	}

	// TODO Optimize - reuse something here
	@Override
	public OutputSpreadsheet analyze() {
		List<InputSpreadsheet> inputSpreadsheets = Lists.newArrayList();
		
		// Extract and formalize the necessary data
		for (String spreadsheet : AnalystUtil.getIBDRawSpreadsheets()) {
			SpreadsheetScanner strategy = new RatingScanner();
			inputSpreadsheets.add(strategy.extract(spreadsheet));
		}
		
		return assistant.analyze(inputSpreadsheets);
	}
	
	public static void main(String[] args) throws IOException {
		Analyst analyst = new BasicAnalyst(new FullAnalyzer());
		analyst.generateResultSpreadsheet();
		
		analyst = new BasicAnalyst(new IBD50PlusSectorLeaderAnalyzer());
		analyst.generateResultSpreadsheet();
		
		analyst = new BasicAnalyst(new HighOccurrenceAnalyzer());
		analyst.generateResultSpreadsheet();
	}
}