package org.shiqing.ibd.filter;

import org.shiqing.ibd.model.OutputSpreadsheet;

/**
 * Default filter - just silly return the input outputSpreadsheet without any filtering
 * 
 * @author shiqing
 *
 */
public class DefaultFilter implements Filter {

	public OutputSpreadsheet filtrate(OutputSpreadsheet outputSpreadsheet) {
		return outputSpreadsheet;
	}

}
