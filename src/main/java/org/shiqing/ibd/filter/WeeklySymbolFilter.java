package org.shiqing.ibd.filter;

import java.io.File;

import org.shiqing.ibd.config.ConfigFactory;

/**
 * A weekly filter which uses YOUR WEEKLY REVIVEW.xls as the filtering criteria.
 * 
 * The reason to have this filter is :
 * 1. Right now the IBD50 + Sector Leader is only based on the raw spreadsheets from IBD on each Friday,
 *    even through the spreadsheet is updated each day. (Lack of automation to do this.)
 * 2. So there could be two situation which is not ideal : 
 *    1) A stock showing up from Monday to Thursday but miss Friday. But this is fine, we only care about
 *       the stock which is still showing on the list by the end of each week. So if it disappear from Friday,
 *       then that means it's not good and we don't want it any more.
 *    2) A stock NOT showing from Monday to Thursday but only showing on Friday. We want to find a true leader
 *       instead of some magical stock just showing up once. So to avoid this situation, we use the weekly review
 *       spreadsheet as a defender which list the stocks performing good across the week. 
 *       So if a stock is just a flash in the pan, it will be filtered by this.
 *       
 *  Also, ideally as mentioned above. If finally we can make this program running daily, this is not necessary any more.
 * 
 * @author shiqing
 *
 */
public class WeeklySymbolFilter extends SymbolFilter {
	
	/**
	 * Return "YOUR WEEKLY REVIEW.xls"
	 */
	@Override
	protected String getFilteringCriteriaFile() {
		File root = new File((String)ConfigFactory.get().getPropertiesProvider().getValue("path.root"));
		File[] files = root.listFiles();
		
		for (File file : files) {
			if (file.isFile() && file.getName().contains("WEEKLY")) {
				return (String)ConfigFactory.get().getPropertiesProvider().getValue("path.root") + file.getName();
			}
		}
		
		return null;
	}
}