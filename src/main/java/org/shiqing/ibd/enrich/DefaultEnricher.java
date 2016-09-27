package org.shiqing.ibd.enrich;

import org.shiqing.ibd.model.OutputSpreadsheet;

/**
 * Default enricher - just silly return the input outputSpreadsheet without any enriching
 * 
 * @author shiqing
 *
 */
public class DefaultEnricher implements Enricher {

	public OutputSpreadsheet enrich(OutputSpreadsheet outputSpreadsheet) {
		// Update the context first
		EnricherUtil.updateContext(this.getClass().getSimpleName());
		
		return outputSpreadsheet;
	}

}
