package org.shiqing.ibd.analyst;

import java.io.File;
import java.util.List;

import org.shiqing.ibd.config.ConfigFactory;

import com.google.common.collect.Lists;

public class AnalystUtil {
	
	/**
	 * Get all IBD raw spreadsheets
	 * @return
	 */
	public static List<String> getIBDRawSpreadsheets() {
		List<String> spreadsheets = Lists.newArrayList();
		
		File root = new File((String)ConfigFactory.get().getPropertiesProvider().getValue("path.root"));
		File[] files = root.listFiles();
		
		for (File file : files) {
			if (file.isFile() && !file.getName().equals("result.xls") && file.getName().endsWith(".xls")) {
				spreadsheets.add((String)ConfigFactory.get().getPropertiesProvider().getValue("path.root") + file.getName());
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
		
		File root = new File((String)ConfigFactory.get().getPropertiesProvider().getValue("path.result"));
		File[] files = root.listFiles();
		
		for (File file : files) {
			if (file.isFile() && file.getName().contains("ibd50_plus_sector_leader")) {
				spreadsheets.add((String)ConfigFactory.get().getPropertiesProvider().getValue("path.result") + file.getName());
			}
		}
		
		return spreadsheets;
	}
	
	/**
	 * Get IPO LEADERS.xls raw spreadsheet
	 * @return
	 */
	public static String getIPOSpreadsheet() {
		File root = new File((String)ConfigFactory.get().getPropertiesProvider().getValue("path.root"));
		File[] files = root.listFiles();
		
		for (File file : files) {
			if (file.isFile() && file.getName().contains("IPO")) {
				return (String)ConfigFactory.get().getPropertiesProvider().getValue("path.root") + file.getName();
			}
		}
		
		return null;
	}
}
