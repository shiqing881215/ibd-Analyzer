package org.shiqing.ibd.analyst;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.shiqing.ibd.config.ConfigFactory;
import org.shiqing.ibd.model.TimePeriod;
import org.shiqing.ibd.services.QuoteService;

import com.google.common.collect.Lists;

/**
 * 
 * Stand alone analyst for index - Dow Jones, Nasdqa, S&P500
 * 
 * This analyst doesn't extend {@link Analyst} cause it just print out 
 * the simple result into text file instead of separate spreadsheet.
 * 
 * @author shiqing
 *
 */
public class IndexAnalyst {
	
	private static final List<TimePeriod> TIME_PERIODS = 
			Lists.newArrayList(TimePeriod.ONE_WEEK, TimePeriod.ONE_MONTH, TimePeriod.THREE_MONTHS, TimePeriod.SIX_MONTHS);
	private static final List<String> INDICES = 
			Lists.newArrayList("^DJI", "^GSPC", "^IXIC");
	
	public static void main(String[] args) throws Exception {
		Path file = Paths.get(ConfigFactory.get().getPropertiesProvider().getValue("path.result") + "index.txt");
		QuoteService quoteService = (QuoteService)ConfigFactory.get().getBean("quoteService");
		
		Files.write(file, Arrays.asList(new Date().toString()), Charset.forName("UTF-8"));
		
		for (String index : INDICES) {
			for (TimePeriod timePeriod : TIME_PERIODS) {
				Pair<Double, Double> quotes = quoteService.getHistoryQuotes(index, timePeriod);
				Files.write (file, 
						Arrays.asList(index + " for " + timePeriod.getName() + " " + MessageFormat.format("{0,number,#.##%}", (quotes.getLeft() - quotes.getRight()) / quotes.getRight())),
						StandardOpenOption.APPEND);
			}
		}
		
		System.out.println("Index result done......");
	}
}
