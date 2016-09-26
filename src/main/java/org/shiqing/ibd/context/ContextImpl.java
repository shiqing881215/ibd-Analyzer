package org.shiqing.ibd.context;

public class ContextImpl implements Context {

	private String scannerName;
	private String analyzerName;
	private String filterName;
	private String enricherName;
	private String printerName;
	
	public String getScannerName() {
		return scannerName;
	}

	public String getAnalyzerName() {
		return analyzerName;
	}

	public String getFilterName() {
		return filterName;
	}

	public String getEnricherName() {
		return enricherName;
	}

	public String getPrinterName() {
		return printerName;
	}

	public void setScannerName(String scannerName) {
		this.scannerName = scannerName;
	}

	public void setAnalyzerName(String analyzerName) {
		this.analyzerName = analyzerName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

	public void setEnricherName(String enricherName) {
		this.enricherName = enricherName;
	}

	public void setPrinterName(String printerName) {
		this.printerName = printerName;
	}

}