package org.shiqing.ibd;

import java.util.List;

import org.shiqing.ibd.analyzer.Analyzer;
import org.shiqing.ibd.analyzer.IBD50PlusSectorLeaderHistoryAnalyzer;
import org.shiqing.ibd.model.InputSpreadsheet;
import org.shiqing.ibd.model.OutputSpreadsheet;
import org.shiqing.ibd.scanner.IBD50AndSectorLeaderResultScanner;
import org.shiqing.ibd.scanner.SpreadsheetScanner;

import com.google.common.collect.Lists;

/**
 * 
 * Golden analyst analyze based on the ibd50_plus_sector_leader result.
 * The major goal for this is to see which stock stays on these two lists most frequently and 
 * has the best continuity in a certain time line.
 * 
 * @author shiqing
 *
 */
public class GoldenAnalyst extends Analyst {

	public GoldenAnalyst(Analyzer assistant) {
		super(assistant);
	}

	@Override
	public OutputSpreadsheet analyze() {
		List<InputSpreadsheet> inputSpreadsheets = Lists.newArrayList();
		
		// Extract and formalize the necessary data
		for (String spreadsheet : AnalystUtil.getIBD50AndSectorLeaderResultSpreadsheets()) {
			SpreadsheetScanner scanner = new IBD50AndSectorLeaderResultScanner();
			inputSpreadsheets.add(scanner.extract(spreadsheet));
		}
		
		return assistant.analyze(inputSpreadsheets);
	}
	
	public static void main(String[] args) {
		Analyst analyst = new GoldenAnalyst(new IBD50PlusSectorLeaderHistoryAnalyzer());
		analyst.assistant.generateResultSpreadsheet(analyst.analyze());
	}
}
