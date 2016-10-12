package org.shiqing.ibd.analyst;

import java.util.List;

import org.apache.log4j.Logger;
import org.shiqing.ibd.analyzer.AccDisAnalyzer;
import org.shiqing.ibd.analyzer.Analyzer;
import org.shiqing.ibd.enrich.DefaultEnricher;
import org.shiqing.ibd.enrich.Enricher;
import org.shiqing.ibd.filter.DefaultFilter;
import org.shiqing.ibd.filter.Filter;
import org.shiqing.ibd.printer.AccDisSpreadsheetPrinter;
import org.shiqing.ibd.printer.SpreadsheetPrinter;
import org.shiqing.ibd.scanner.RatingScanner;
import org.shiqing.ibd.scanner.SpreadsheetScanner;

import com.google.common.collect.Lists;

/**
 * 
 * Analyst for accumulation and distribution.
 * 
 * @author shiqing
 *
 */
public class AccDisAnalyst extends Analyst {

	private static final Logger logger = Logger.getLogger(AccDisAnalyst.class);
	
	public AccDisAnalyst(SpreadsheetScanner scanner, Analyzer analyzer, List<Filter> filters, List<Enricher> enrichers,
			SpreadsheetPrinter printer) {
		super(scanner, analyzer, filters, enrichers, printer);
	}

	@Override
	protected List<String> getInputSpreadsheetPaths() {
		return AnalystUtil.getIBDRawSpreadsheets();
	}

	public static void main(String[] args) {
		logger.info("Running AccDisAnalyst......");
		
		SpreadsheetScanner ratingScanner = new RatingScanner();
		Analyzer accDisAnalyzer = new AccDisAnalyzer();
		Filter defaultFilter = new DefaultFilter();
		Enricher defaultEnricher = new DefaultEnricher();
		SpreadsheetPrinter accDisSpreadsheetPrinter = new AccDisSpreadsheetPrinter();
		
		Analyst analyst = new AccDisAnalyst(ratingScanner, 
				accDisAnalyzer, 
				Lists.newArrayList(defaultFilter), 
				Lists.newArrayList(defaultEnricher), 
				accDisSpreadsheetPrinter);
		
		analyst.brainstorm();
	}
}
