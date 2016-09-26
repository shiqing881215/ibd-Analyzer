package org.shiqing.ibd.analyzer;

import java.util.List;

import org.shiqing.ibd.model.InputSpreadsheet;
import org.shiqing.ibd.model.OutputSpreadsheet;

/**
 * 
 * Interface for any analyze strategy.
 * Any strategy to analyze the stock list data needs to implement this interface.
 * 
 * @author shiqing
 *
 */
public interface Analyzer {
	
	/**
	 * Analyze a list of {@link InputSpreadsheet} and generate specific result based on the strategy.
	 * @return A {@link OutputSpreadsheet} that can be used to print out a single result spreadsheet.
	 */
	public OutputSpreadsheet analyze(List<InputSpreadsheet> inputSpreadsheets);
}
