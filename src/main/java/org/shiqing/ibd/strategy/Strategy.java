package org.shiqing.ibd.strategy;

import org.shiqing.ibd.model.StockList;

/**
 * 
 * @author shiqing
 * Interface of any strategy to implement to define what kind of data to extract from the spreadsheet.
 *
 */
public interface Strategy {
	
	/**
	 * Extract specific data from the spreadsheet based on the strategy.
	 * @return A {@link StockList} to represent this spreadsheet
	 */
	public StockList extract(String filePath);
}
