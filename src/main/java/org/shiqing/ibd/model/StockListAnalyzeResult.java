package org.shiqing.ibd.model;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * @author shiqing
 *
 */
public class StockListAnalyzeResult {
	
	private Map<String, StockAnalyzeResult> result;

	public StockListAnalyzeResult() {
		super();
		result = Maps.newHashMap();
	}

	public Map<String, StockAnalyzeResult> getResult() {
		return result;
	}
	
	/**
	 * Add a new stock analyze result
	 * @param stock
	 */
	public void addStockAnalyzeResult(Stock stock, String involvedSpreadsheet) {
		String symbol = stock.getSymbol();
		// If exists, update occurrence and involvedSpreadsheets
		if (result.containsKey(symbol)) {
			result.get(symbol).setOccurrence(result.get(symbol).getOccurrence() + 1);
			result.get(symbol).getInvolvedSpreadsheets().add(involvedSpreadsheet);
		} else { 
			StockAnalyzeResult stockAnalyzeResult = new StockAnalyzeResult(
					symbol, stock.getCompanyName(), 1, Lists.newArrayList(involvedSpreadsheet));
			result.put(symbol, stockAnalyzeResult);
		}
	}
	
	/**
	 * Add an existing stock analyze result entry.
	 * @param existingStockAnalyzeResult
	 */
	public void addExistingStockAnalyzeResult(Entry<String, StockAnalyzeResult> existingStockAnalyzeResult) {
		result.entrySet().add(existingStockAnalyzeResult);
	}
}