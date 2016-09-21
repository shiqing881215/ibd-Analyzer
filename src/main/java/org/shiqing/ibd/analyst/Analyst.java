package org.shiqing.ibd.analyst;

import java.util.List;

import org.shiqing.ibd.analyzer.Analyzer;
import org.shiqing.ibd.enrich.Enricher;
import org.shiqing.ibd.filter.Filter;
import org.shiqing.ibd.model.InputSpreadsheet;
import org.shiqing.ibd.model.OutputSpreadsheet;
import org.shiqing.ibd.printer.SpreadsheetPrinter;
import org.shiqing.ibd.scanner.SpreadsheetScanner;

import com.google.common.collect.Lists;

/**
 * Fundamental analyst.
 * It composes {@link SpreadsheetScanner} {@link Analyzer} and {@link SpreadsheetPrinter} to do the real work.
 * And organize them is a certain way.
 * 
 * @author shiqing
 *
 */
public abstract class Analyst implements IAnalyst {
	
	protected SpreadsheetScanner scanner;
	protected Analyzer analyzer;
	protected List<Filter> filters;
	protected List<Enricher> enrichers;
	protected SpreadsheetPrinter printer;
	
	public Analyst(SpreadsheetScanner scanner, Analyzer analyzer,  List<Filter> filters, List<Enricher> enrichers, SpreadsheetPrinter printer) {
		super();
		this.scanner = scanner;
		this.analyzer = analyzer;
		this.filters = filters;
		this.enrichers = enrichers;
		this.printer = printer;
	}
	
	protected List<InputSpreadsheet> extract(List<String> filePaths) {
		List<InputSpreadsheet> inputSpreadsheets = Lists.newArrayList();
		for (String filePath : filePaths) {
			inputSpreadsheets.add(scanner.extract(filePath));
		}
		return inputSpreadsheets;
	}
	
	protected OutputSpreadsheet analyze(List<InputSpreadsheet> stockLists) {
		return analyzer.analyze(stockLists);
	}
	
	protected OutputSpreadsheet filtrate(OutputSpreadsheet outputSpreadsheet) {
		OutputSpreadsheet output = outputSpreadsheet;
		for (Filter filter : filters) {
			output = filter.filtrate(output);
		}
		return output;
	}
	
	protected OutputSpreadsheet enrich(OutputSpreadsheet outputSpreadsheet) {
		OutputSpreadsheet output = outputSpreadsheet;
		for (Enricher enricher : enrichers) {
			output = enricher.enrich(output);
		}
		return output;
	}
	
	protected void generateResultSpreadsheet(OutputSpreadsheet outputSpreadsheet) {
		printer.generateResultSpreadsheet(outputSpreadsheet);
	}

	/**
	 * 6 steps here : 
	 * 
	 * 1. Get the input spreadsheets paths
	 * 2. Extract the data
	 * 3. Analyze the data
	 * 4. Filter the analyze result
	 * 5. Enrich the analyze result
	 * 6. Generate the result spreadsheets
	 */
	public void brainstorm() {
		generateResultSpreadsheet(enrich(filtrate(analyze(extract(getInputSpreadsheetPaths())))));
	}
	
	protected abstract List<String> getInputSpreadsheetPaths();
}
