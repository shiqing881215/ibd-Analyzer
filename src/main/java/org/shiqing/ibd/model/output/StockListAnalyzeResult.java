package org.shiqing.ibd.model.output;

import java.util.Map;
import java.util.Map.Entry;

import org.shiqing.ibd.model.OutputSpreadsheet;
import org.shiqing.ibd.model.input.Stock;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * A data pojo to represent a bunch of stock analyze result.
 * The major purpose for this pojo is to generate the result spreadsheet.
 * 
 * @author shiqing
 *
 */
public class StockListAnalyzeResult implements OutputSpreadsheet {
	
	// Key is the symbol of the stock
	private Map<String, StockAnalyzeResult> result;

	public StockListAnalyzeResult() {
		super();
		result = Maps.newHashMap();
	}

	public Map<String, StockAnalyzeResult> getResult() {
		return result;
	}
	
	/**
	 * Add a new or existing stock analyze result
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
