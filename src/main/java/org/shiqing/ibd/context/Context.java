package org.shiqing.ibd.context;

import java.util.Set;

/**
 * 
 * Interface defining the data shape storing in the context
 * along with the whole analyze process.
 * 
 * So with this context strategy, each step in the whole process
 * could have a clear overall view about what happen before reaching
 * current step.
 * 
 * @author shiqing
 *
 */
public interface Context {
	
	public String getScannerName();
	
	public void setScannerName(String scannerName);
	
	public String getAnalyzerName();
	
	public void setAnalyzerName(String analyzerName);
	
	public Set<String> getFilterNames();
	
	public void addFilterName(String filterName);
	
	public Set<String> getEnricherNames();
	
	public void addEnricherName(String enricherName);
	
	public String getPrinterName();
	
	public void setPrinterName(String printerName);
}
