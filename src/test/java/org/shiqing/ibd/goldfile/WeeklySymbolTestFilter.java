package org.shiqing.ibd.goldfile;

import java.io.File;

import org.shiqing.ibd.config.ConfigFactory;
import org.shiqing.ibd.filter.WeeklySymbolFilter;

public class WeeklySymbolTestFilter extends WeeklySymbolFilter {

	@Override
	protected String getFilteringCriteriaFile() {
		File root = new File((String)ConfigFactory.get().getPropertiesProvider().getValue("path.test.root"));
		File[] files = root.listFiles();
		
		for (File file : files) {
			if (file.isFile() && file.getName().contains("WEEKLY")) {
				return ConfigFactory.get().getPropertiesProvider().getValue("path.test.root") + file.getName();
			}
		}
		
		return null;
	}
	
}
