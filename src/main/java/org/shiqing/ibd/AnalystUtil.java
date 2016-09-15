package org.shiqing.ibd;

import java.io.File;
import java.util.List;

import com.google.common.collect.Lists;

public class AnalystUtil {
	
	private static final String ROOT_DIRECTORY = "/Users/Rossi/Documents/IBD/";
	private static final String RESULT_DIRECTORY = ROOT_DIRECTORY + "results/";
	
	public static List<String> getIBDRawSpreadsheets() {
		List<String> spreadsheets = Lists.newArrayList();
		
		File root = new File(ROOT_DIRECTORY);
		File[] files = root.listFiles();
		
		// TODO Remove hardcode directory
		for (File file : files) {
			if (file.isFile() && !file.getName().equals("result.xls") && file.getName().endsWith(".xls")) {
				spreadsheets.add(ROOT_DIRECTORY + file.getName());
			}
		}
		
		return spreadsheets;
	}
	
	/**
	 * Get all the Ibd50SectorLeader results
	 * @return
	 */
	public static List<String> getIBD50AndSectorLeaderResultSpreadsheets() {
		List<String> spreadsheets = Lists.newArrayList();
		
		File root = new File(RESULT_DIRECTORY);
		File[] files = root.listFiles();
		
		// TODO Remove hardcode directory
		for (File file : files) {
			if (file.isFile() && file.getName().contains("ibd50_plus_sector_leader")) {
				spreadsheets.add(RESULT_DIRECTORY + file.getName());
			}
		}
		
		return spreadsheets;
	}
}
