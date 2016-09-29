package org.shiqing.ibd.filter;

import java.io.File;

import org.shiqing.ibd.config.ConfigFactory;

/**
 * 
 * IBD50 spreadsheet symbol filter.
 * 
 * @author shiqing
 *
 */
public class IBD50SymbolFilter extends SymbolFilter {

	/**
	 * Return IBD 50 spreadsheet name
	 */
	@Override
	protected String getFilteringCriteriaFile() {
		File root = new File((String)ConfigFactory.get().getPropertiesProvider().getValue("path.root"));
		File[] files = root.listFiles();
		
		for (File file : files) {
			if (file.isFile() && file.getName().contains("IBD") && file.getName().contains("50")) {
				return (String)ConfigFactory.get().getPropertiesProvider().getValue("path.root") + file.getName();
			}
		}
		
		return null;
	}

}
