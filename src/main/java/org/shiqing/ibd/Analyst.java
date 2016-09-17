package org.shiqing.ibd;

import java.util.List;

import org.shiqing.ibd.analyzer.Analyzer;
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
	protected SpreadsheetPrinter printer;
	protected Filter filter;
	
	public Analyst(SpreadsheetScanner scanner, Analyzer analyzer, SpreadsheetPrinter printer, Filter filter) {
		super();
		this.scanner = scanner;
		this.analyzer = analyzer;
		this.printer = printer;
		this.filter = filter;
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
		return filter.filtrate(outputSpreadsheet);
	}
	
	protected void generateResultSpreadsheet(OutputSpreadsheet outputSpreadsheet) {
		printer.generateResultSpreadsheet(outputSpreadsheet);
	}

	/**
	 * 5 steps here : 
	 * 1. Get the input spreadsheets paths
	 * 2. Extract the data
	 * 3. Analyze the data
	 * 4. Filter the analyze result
	 * 5. Generate the result spreadsheets
	 */
	public void brainstorm() {
		generateResultSpreadsheet(filtrate(analyze(extract(getInputSpreadsheetPaths()))));
	}
	
	protected abstract List<String> getInputSpreadsheetPaths();
}
