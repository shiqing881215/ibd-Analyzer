package org.shiqing.ibd;

import java.io.IOException;
import java.util.List;

import org.shiqing.ibd.analyzer.Analyzer;
import org.shiqing.ibd.analyzer.FullAnalyzer;
import org.shiqing.ibd.analyzer.HighOccurrenceAnalyzer;
import org.shiqing.ibd.analyzer.IBD50AndSectorLeaderAnalyzer;
import org.shiqing.ibd.printer.FullSpreadsheetPrinter;
import org.shiqing.ibd.printer.HighOccurrenceSpreadsheetPrinter;
import org.shiqing.ibd.printer.IBD50AndSectorLeaderSpreadsheetPrinter;
import org.shiqing.ibd.printer.SpreadsheetPrinter;
import org.shiqing.ibd.scanner.RatingScanner;
import org.shiqing.ibd.scanner.SpreadsheetScanner;

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
	
	public BasicAnalyst(SpreadsheetScanner scanner, Analyzer analyzer, SpreadsheetPrinter printer) {
		super(scanner, analyzer, printer);
	}
	
	@Override
	protected List<String> getInputSpreadsheetPaths() {
		return AnalystUtil.getIBDRawSpreadsheets();
	}

	public static void main(String[] args) throws IOException {
		Analyst analyst = new BasicAnalyst(new RatingScanner(), new FullAnalyzer(), new FullSpreadsheetPrinter());
		analyst.brainstorm();
		
		analyst = new BasicAnalyst(new RatingScanner(), new IBD50AndSectorLeaderAnalyzer(), new IBD50AndSectorLeaderSpreadsheetPrinter());
		analyst.brainstorm();
		
		analyst = new BasicAnalyst(new RatingScanner(), new HighOccurrenceAnalyzer(), new HighOccurrenceSpreadsheetPrinter());
		analyst.brainstorm();
	}
}