package org.shiqing.ibd.services;

import org.shiqing.ibd.config.ConfigFactory;
import org.shiqing.ibd.model.TimePeriod;

public class QuoteServiceTest {

	public static void main(String[] args) throws Exception {
//		((QuoteService)ConfigFactory.get().getBean("quoteService")).getHistoryQuotes("^IXIC", TimePeriod.ONE_WEEK);
		((QuoteService)ConfigFactory.get().getBean("quoteService")).getHistoryQuotes("CRM", TimePeriod.ONE_WEEK);
	}
}
