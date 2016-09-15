package org.shiqing.ibd.analyzer;

import java.util.List;

import org.shiqing.ibd.model.Spreadsheet;
import org.shiqing.ibd.model.input.StockList;
import org.shiqing.ibd.model.output.StockListAnalyzeResult;

/**
 * 
 * Interface for any analyze strategy.
 * Any strategy to analyze the stock list data needs to implement this interface.
 * 
 * @author shiqing
 *
 */
public interface Analyzer {
	
	public static final String ROOT_DIRECTORY = "/Users/Rossi/Documents/IBD/";
	public static final String RESULT_DIRECTORY = "results/"; 
	
	/**
	 * Analyze a list of {@link StockList} and generate specific result based on the strategy.
	 * @return A {@link StockListAnalyzeResult} that can be used to print out a single result spreadsheet.
	 */
	public StockListAnalyzeResult analyze(List<Spreadsheet> stockLists);
	
	/**
	 * Generate a spreadsheet that contains the analyze result
	 * @param stockListAnalyzeResult
	 */
	public void generateResultSpreadsheet(StockListAnalyzeResult stockListAnalyzeResult);
}
