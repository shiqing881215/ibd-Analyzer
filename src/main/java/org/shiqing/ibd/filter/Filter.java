package org.shiqing.ibd.filter;

import org.shiqing.ibd.model.OutputSpreadsheet;

/**
 * Interface providing the hook to apply additional filtering on analyze result.
 * 
 * @author shiqing
 *
 */
public interface Filter {
	
	/**
	 * Filter the analyze result based on certain criteria
	 * @param outputSpreadsheet - input analyze result
	 * @return {@link OutputSpreadsheet} - a filtered analyze result
	 */
	public OutputSpreadsheet filtrate(OutputSpreadsheet outputSpreadsheet);
	
}
