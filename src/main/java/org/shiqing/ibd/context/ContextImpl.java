package org.shiqing.ibd.context;

import java.util.Set;

import com.google.common.collect.Sets;

public class ContextImpl implements Context {

	private String scannerName;
	private String analyzerName;
	private Set<String> filterNames;
	private Set<String> enricherNames;
	private String printerName;
	
	public ContextImpl() {
		filterNames = Sets.newHashSet();
		enricherNames = Sets.newHashSet();
	}
	
	public String getScannerName() {
		return scannerName;
	}

	public String getAnalyzerName() {
		return analyzerName;
	}

	public Set<String> getFilterNames() {
		return filterNames;
	}

	public Set<String> getEnricherNames() {
		return enricherNames;
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

	public void addFilterName(String filterName) {
		this.filterNames.add(filterName);
	}

	public void addEnricherName(String enricherName) {
		this.enricherNames.add(enricherName);
	}

	public void setPrinterName(String printerName) {
		this.printerName = printerName;
	}

}