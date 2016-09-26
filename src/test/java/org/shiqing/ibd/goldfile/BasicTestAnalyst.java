package org.shiqing.ibd.goldfile;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.shiqing.ibd.analyst.Analyst;
import org.shiqing.ibd.analyst.BasicAnalyst;
import org.shiqing.ibd.analyzer.Analyzer;
import org.shiqing.ibd.analyzer.FullAnalyzer;
import org.shiqing.ibd.analyzer.HighOccurrenceAnalyzer;
import org.shiqing.ibd.analyzer.IBD50AndSectorLeaderAnalyzer;
import org.shiqing.ibd.config.ConfigFactory;
import org.shiqing.ibd.enrich.DefaultEnricher;
import org.shiqing.ibd.enrich.Enricher;
import org.shiqing.ibd.filter.DefaultFilter;
import org.shiqing.ibd.filter.Filter;
import org.shiqing.ibd.filter.WeeklySymbolFilter;
import org.shiqing.ibd.printer.SpreadsheetPrinter;
import org.shiqing.ibd.scanner.RatingScanner;
import org.shiqing.ibd.scanner.SpreadsheetScanner;

import com.google.common.collect.Lists;

/**
 * 
 * Test implementation of {@link BasicAnalyst}.
 * Major purpose here is to override the scan file directory.
 * 
 * @author shiqing
 *
 */
public class BasicTestAnalyst extends BasicAnalyst {

	public BasicTestAnalyst(SpreadsheetScanner scanner, Analyzer analyzer, List<Filter> filters,
			List<Enricher> enrichers, SpreadsheetPrinter printer) {
		super(scanner, analyzer, filters, enrichers, printer);
	}

	@Override
	protected List<String> getInputSpreadsheetPaths() {
		return getGoldFileTestInputSpreadsheets();
	}
	
	private List<String> getGoldFileTestInputSpreadsheets() {
		List<String> spreadsheets = Lists.newArrayList();
		
		File root = new File("/Users/Rossi/Documents/workspace/ibd/src/test/java/org/shiqing/ibd/goldfile/raw/");
		File[] files = root.listFiles();
		
		for (File file : files) {
			if (file.isFile() && !file.getName().equals("result.xls") && file.getName().endsWith(".xls")) {
				spreadsheets.add((String)ConfigFactory.get().getPropertiesProvider().getValue("path.root") + file.getName());
			}
		}
		
		return spreadsheets;
	}
	
	public static void generateTestResult() throws IOException {
		SpreadsheetScanner ratingScanner = new RatingScanner();
		
		Analyzer fullAnalyzer = new FullAnalyzer();
		Analyzer ibd50AndSectorLeaderAnalyzer = new IBD50AndSectorLeaderAnalyzer();
		Analyzer highOccurrenceAnalyzer = new HighOccurrenceAnalyzer();
		
		Filter defaultFilter = new DefaultFilter();
		Filter weeklyFilter = new WeeklySymbolFilter();
		
		Enricher defaultEnricher = new DefaultEnricher();
		
		SpreadsheetPrinter fullPrinter = new FullSpreadsheetTestPrinter();
		SpreadsheetPrinter ibd50AndSectorLeaderPrinter = new IBD50AndSectorLeaderSpreadsheetTestPrinter();
		SpreadsheetPrinter highOccurrencePrinter = new HighOccurrenceSpreadsheetTestPrinter();
		
		Analyst analyst = new BasicTestAnalyst(ratingScanner, 
				fullAnalyzer, 
				Lists.newArrayList(defaultFilter), 
				Lists.newArrayList(defaultEnricher), 
				fullPrinter);
		analyst.brainstorm();
		
		analyst = new BasicTestAnalyst(ratingScanner, 
				ibd50AndSectorLeaderAnalyzer, 
				Lists.newArrayList(weeklyFilter), 
				Lists.newArrayList(defaultEnricher), 
				ibd50AndSectorLeaderPrinter);
		analyst.brainstorm();
		
		analyst = new BasicTestAnalyst(ratingScanner, 
				highOccurrenceAnalyzer, 
				Lists.newArrayList(defaultFilter), 
				Lists.newArrayList(defaultEnricher), 
				highOccurrencePrinter);
		analyst.brainstorm();
	}
}
