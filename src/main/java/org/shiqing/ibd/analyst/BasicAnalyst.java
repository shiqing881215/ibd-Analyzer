package org.shiqing.ibd.analyst;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
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
	
	private static final Logger logger = Logger.getLogger(BasicAnalyst.class);
	
	public BasicAnalyst(SpreadsheetScanner scanner, Analyzer analyzer, List<Filter> filters, List<Enricher> enrichers, SpreadsheetPrinter printer) {
		super(scanner, analyzer, filters, enrichers, printer);
	}
	
	@Override
	protected List<String> getInputSpreadsheetPaths() {
		return AnalystUtil.getIBDRawSpreadsheets();
	}

	public static void main(String[] args) throws IOException {
		logger.info("Running BasicAnalyst......");
		
		// TODO Reuse the ratingScanner and defaultFilter
		Filter defaultFilter = new DefaultFilter();
		Filter weeklyFilter = new WeeklyFilter();
		Enricher defaultEnricher = new DefaultEnricher();
		
		Analyst analyst = new BasicAnalyst(new RatingScanner(), new FullAnalyzer(), Lists.newArrayList(defaultFilter), Lists.newArrayList(defaultEnricher), new FullSpreadsheetPrinter());
		analyst.brainstorm();
		
		analyst = new BasicAnalyst(new RatingScanner(), new IBD50AndSectorLeaderAnalyzer(), Lists.newArrayList(weeklyFilter), Lists.newArrayList(defaultEnricher), new IBD50AndSectorLeaderSpreadsheetPrinter());
		analyst.brainstorm();
		
		analyst = new BasicAnalyst(new RatingScanner(), new HighOccurrenceAnalyzer(), Lists.newArrayList(defaultFilter), Lists.newArrayList(defaultEnricher), new HighOccurrenceSpreadsheetPrinter());
		analyst.brainstorm();
	}
}