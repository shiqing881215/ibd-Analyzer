package org.shiqing.ibd.analyst;

import java.util.List;

import org.apache.log4j.Logger;
import org.shiqing.ibd.analyzer.Analyzer;
import org.shiqing.ibd.analyzer.IBD50AndSectorLeaderHistoryAnalyzer;
import org.shiqing.ibd.enrich.ContinuityEnricher;
import org.shiqing.ibd.enrich.Enricher;
import org.shiqing.ibd.enrich.QuotePerformanceEnricher;
import org.shiqing.ibd.filter.DefaultFilter;
import org.shiqing.ibd.filter.Filter;
import org.shiqing.ibd.printer.IBD50AndSectorLeaderHistorySpreadsheetPrinter;
import org.shiqing.ibd.printer.SpreadsheetPrinter;
import org.shiqing.ibd.scanner.IBD50AndSectorLeaderResultScanner;
import org.shiqing.ibd.scanner.SpreadsheetScanner;

import com.google.common.collect.Lists;

/**
 * 
 * Golden analyst analyze based on the ibd50_plus_sector_leader result.
 * The major goal for this is to see which stock stays on these two lists most frequently and 
 * has the best continuity in a certain time period.
 * 
 * @author shiqing
 *
 */
public class GoldenAnalyst extends Analyst {
	
	private static final Logger logger = Logger.getLogger(GoldenAnalyst.class);
	
	public GoldenAnalyst(SpreadsheetScanner scanner, Analyzer analyzer, List<Filter> filters, List<Enricher> enrichers, SpreadsheetPrinter printer) {
		super(scanner, analyzer, filters, enrichers, printer);
	}

	@Override
	protected List<String> getInputSpreadsheetPaths() {
		return AnalystUtil.getIBD50AndSectorLeaderResultSpreadsheets();
	}
	
	public static void main(String[] args) {
		logger.info("Running GoldenAnalyst......");
		
		Filter defaultFilter = new DefaultFilter();
		Enricher performanceEnricher = new QuotePerformanceEnricher();
		Enricher continuityEnricher = new ContinuityEnricher();
		
		Analyst analyst = new GoldenAnalyst(new IBD50AndSectorLeaderResultScanner(), 
				new IBD50AndSectorLeaderHistoryAnalyzer(), 
				Lists.newArrayList(defaultFilter), 
				Lists.newArrayList(performanceEnricher, continuityEnricher), 
				new IBD50AndSectorLeaderHistorySpreadsheetPrinter());
		analyst.brainstorm();
	}
}
