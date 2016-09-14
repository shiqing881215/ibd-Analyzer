package org.shiqing.ibd.model.raw;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

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
			if (!result.get(symbol).getInvolvedSpreadsheets().contains(involvedSpreadsheet)) {
				result.get(symbol).setOccurrence(result.get(symbol).getOccurrence() + 1);
				result.get(symbol).getInvolvedSpreadsheets().add(involvedSpreadsheet);
			}
		} else { 
			StockAnalyzeResult stockAnalyzeResult = new StockAnalyzeResult(
					symbol, stock.getCompanyName(), 1, Sets.newHashSet(involvedSpreadsheet));
			result.put(symbol, stockAnalyzeResult);
		}
	}
	
	/**
	 * Add an existing stock analyze result entry.
	 * @param existingStockAnalyzeResult
	 */
	public void addExistingStockAnalyzeResult(Entry<String, StockAnalyzeResult> existingStockAnalyzeResult) {
		result.put(existingStockAnalyzeResult.getKey(), existingStockAnalyzeResult.getValue());
	}
}
