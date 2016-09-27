package org.shiqing.ibd.config;

import org.shiqing.ibd.context.ContextProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * A factory class to deliver all the beans based on requirement.
 * 
 * @author shiqing
 *
 */
public class ConfigFactory {
	
	// Singleton 
	private static ConfigFactory configFactory;
	
	private ConfigFactory() {
		
	}
	
	public static ConfigFactory get() {
		if (configFactory == null) {
			configFactory = new ConfigFactory();
		}
		
		return configFactory;
	}
	
	public Object getBean(String beanName) {
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		return context.getBean(beanName);
	}
	
	// Add a specific method for .properties config file cause it will be used more frequently
	public PropertiesProvider getPropertiesProvider() {
		return (PropertiesProvider)getBean("propertiesProvider");
	}
	
	// Add a specific method for context provider cause it will be used more frequently
	public ContextProvider getContextProvider() {
		return (ContextProvider)getBean("contextProvider");
	}
}
