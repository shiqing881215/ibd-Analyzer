package org.shiqing.ibd.model.output;

import java.util.Map;

import org.shiqing.ibd.model.OutputSpreadsheet;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * 
 * A data pojo represents a bunch of stock analyze result for IBD50 + Sector Leader.
 * 
 * A sample result format : 
 * {
 *  "MXL" : {MXL | Maxlinear.Inc | 3 | 08_21_16, 08_28_16, 09_05_16}
 *  "CRM" : {CRM | Salesforce.com | 3 | 08_21_16, 08_28_16, 09_05_16}
 * }
 * 
 * @author shiqing
 *
 */
public class IBD50AndSectorLeaderStockListAnalyzeResult implements OutputSpreadsheet {
	private Map<String, IBD50AndSectorLeaderStockAnalyzeResult> result;
	
	public IBD50AndSectorLeaderStockListAnalyzeResult() {
		super();
		result = Maps.newHashMap();
	}

	public Map<String, IBD50AndSectorLeaderStockAnalyzeResult> getResult() {
		return result;
	}
	
	/**
	 * Add a new or update an existing single stock analyze result
	 * @param stock
	 * @param newDate
	 */
	public void addStockAnalyzeResult(StockAnalyzeResult stock, String newDate) {
		String symbol = stock.getSymbol();
		if (result.containsKey(symbol)) {
			if (!result.get(symbol).getInvolvedDates().contains(newDate)) {
				result.get(symbol).setOccurrence(result.get(symbol).getOccurrence()+1);
				result.get(symbol).getInvolvedDates().add(newDate);
			}
		} else {
			IBD50AndSectorLeaderStockAnalyzeResult singleStockAnalyzeResult = 
					new IBD50AndSectorLeaderStockAnalyzeResult(stock.getSymbol(), stock.getName(), 1, Sets.newHashSet(newDate));
			result.put(symbol, singleStockAnalyzeResult);
		}
	}
}
