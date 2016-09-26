package org.shiqing.ibd.context;

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
	
	public String getFilterName();
	
	public void setFilterName(String filterName);
	
	public String getEnricherName();
	
	public void setEnricherName(String enricherName);
	
	public String getPrinterName();
	
	public void setPrinterName(String printerName);
}
