package org.shiqing.ibd.config;

import org.shiqing.ibd.services.QuoteService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration file for services package
 * 
 * @author shiqing
 *
 */
@Configuration
public class ServicesConfig {
	
	@Bean(name="quoteService")
	public QuoteService quoteService() {
		return new QuoteService();
	}
}
