package org.shiqing.ibd.enrich;

import org.shiqing.ibd.model.OutputSpreadsheet;

/**
 * 
 * Interface providing the hook to add additional data on analyze result.
 * 
 * @author shiqing
 *
 */
public interface Enricher {

	/**
	 * Enrich the analyze result by additional data based on certain criteria
	 * @param outputSpreadsheet - input analyze result
	 * @return {@link OutputSpreadsheet} - a filtered analyze result
	 */
	public OutputSpreadsheet enrich(OutputSpreadsheet outputSpreadsheet);
}
