package org.shiqing.ibd.analyst;

import java.util.List;

import org.apache.log4j.Logger;
import org.shiqing.ibd.analyzer.Analyzer;
import org.shiqing.ibd.analyzer.IPOAnalyzer;
import org.shiqing.ibd.enrich.Enricher;
import org.shiqing.ibd.enrich.QuotePerformanceEnricher;
import org.shiqing.ibd.filter.DefaultFilter;
import org.shiqing.ibd.filter.Filter;
import org.shiqing.ibd.printer.IPOSpreadsheetPrinter;
import org.shiqing.ibd.printer.SpreadsheetPrinter;
import org.shiqing.ibd.scanner.RatingScanner;
import org.shiqing.ibd.scanner.SpreadsheetScanner;

import com.google.common.collect.Lists;

/**
 * Analyst for IPO stocks
 * 
 * @author shiqing
 *
 */
public class IPOAnalyst extends Analyst {
	
	private static final Logger logger = Logger.getLogger(IPOAnalyst.class);

	public IPOAnalyst(SpreadsheetScanner scanner, Analyzer analyzer, List<Filter> filters, List<Enricher> enrichers,
			SpreadsheetPrinter printer) {
		super(scanner, analyzer, filters, enrichers, printer);
	}

	@Override
	protected List<String> getInputSpreadsheetPaths() {
		return Lists.newArrayList(AnalystUtil.getIPOSpreadsheet());
	}

	public static void main(String[] args) {
		logger.info("Running IPOAnalyst......");
		
		Filter defaultFilter = new DefaultFilter();
		// Don't apply IBD50 right now, it filters too much
//		Filter ibd50Filter = new IBD50SymbolFilter();
		Enricher quotePerformanceEnricher = new QuotePerformanceEnricher();
		
		Analyst analyst = new IPOAnalyst(new RatingScanner(), 
				new IPOAnalyzer(), 
				Lists.newArrayList(defaultFilter), 
				Lists.newArrayList(quotePerformanceEnricher), 
				new IPOSpreadsheetPrinter());
		analyst.brainstorm();
	}
	
}
