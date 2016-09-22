package org.shiqing.ibd.config;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * Class responsible for reading the .properties file.
 * 
 * @author shiqing
 *
 */
public class PropertiesProvider {
	
	public Object getValue(String key) {
        CompositeConfiguration config = new CompositeConfiguration();
        
        try {
        	// add config sources.
        	config.addConfiguration(new PropertiesConfiguration("/Users/Rossi/Documents/workspace/ibd/src/main/resources/config.properties"));
        
        	return config.getProperty(key);
        } catch (Exception e) {
        	return null;
        }
	}
}
