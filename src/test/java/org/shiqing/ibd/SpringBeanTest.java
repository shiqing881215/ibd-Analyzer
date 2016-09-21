package org.shiqing.ibd;

import org.shiqing.ibd.config.AppConfig;
import org.shiqing.ibd.model.TimePeriod;
import org.shiqing.ibd.services.QuoteService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringBeanTest {

	public static void main(String[] args) {
		/*
		//Old way to read config from xml file		
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"SpringBeans.xml"); */

		// New way to read config from JavaConfig
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		QuoteService qs = (QuoteService) context.getBean("quoteService");
		
		System.out.println(qs.getHistoryQuotes("CRM", TimePeriod.ONE_MONTH));
	}

}
