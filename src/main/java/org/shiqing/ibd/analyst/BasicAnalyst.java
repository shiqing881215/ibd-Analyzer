package org.shiqing.ibd.analyst;

import java.io.IOException;
import java.util.List;

import org.shiqing.ibd.analyzer.Analyzer;
import org.shiqing.ibd.analyzer.FullAnalyzer;
import org.shiqing.ibd.analyzer.HighOccurrenceAnalyzer;
import org.shiqing.ibd.analyzer.IBD50AndSectorLeaderAnalyzer;
import org.shiqing.ibd.enrich.DefaultEnricher;
import org.shiqing.ibd.enrich.Enricher;
import org.shiqing.ibd.filter.DefaultFilter;
import org.shiqing.ibd.filter.Filter;
import org.shiqing.ibd.filter.WeeklyFilter;
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
	
	public BasicAnalyst(SpreadsheetScanner scanner, Analyzer analyzer, SpreadsheetPrinter printer, Filter filter, Enricher enricher) {
		super(scanner, analyzer, printer, filter, enricher);
	}
	
	@Override
	protected List<String> getInputSpreadsheetPaths() {
		return AnalystUtil.getIBDRawSpreadsheets();
	}

	public static void main(String[] args) throws IOException {
		// TODO Reuse the ratingScanner and defaultFilter
		Analyst analyst = new BasicAnalyst(new RatingScanner(), new FullAnalyzer(), new FullSpreadsheetPrinter(), new DefaultFilter(), new DefaultEnricher());
		analyst.brainstorm();
		
		analyst = new BasicAnalyst(new RatingScanner(), new IBD50AndSectorLeaderAnalyzer(), new IBD50AndSectorLeaderSpreadsheetPrinter(), new WeeklyFilter(), new DefaultEnricher());
		analyst.brainstorm();
		
		analyst = new BasicAnalyst(new RatingScanner(), new HighOccurrenceAnalyzer(), new HighOccurrenceSpreadsheetPrinter(), new DefaultFilter(), new DefaultEnricher());
		analyst.brainstorm();
	}
}