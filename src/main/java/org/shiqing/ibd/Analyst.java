package org.shiqing.ibd;

import org.shiqing.ibd.analyzer.Analyzer;
import org.shiqing.ibd.model.OutputSpreadsheet;

/**
 * Fundamental analyst.
 * It compose a real analyzer as the delegator which will do all the real work 
 * including scan data + analyze data + print data
 * 
 * @author shiqing
 *
 */
public abstract class Analyst {
	// Delegator
	protected Analyzer assistant;
	
	public Analyst(Analyzer assistant) {
		this.assistant = assistant;
	}
	
	public abstract OutputSpreadsheet analyze();
	
	public void generateResultSpreadsheet() {
		assistant.generateResultSpreadsheet(analyze());
	}
}
