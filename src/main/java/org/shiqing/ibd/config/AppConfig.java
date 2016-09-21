package org.shiqing.ibd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Top level spring configuration.
 * Don't add specific bean here. 
 * Either add a new bean to an existing module or create a new sub-module
 * configuration and import here.
 * 
 * @author shiqing
 *
 */
@Configuration
@Import({ ServicesConfig.class})
public class AppConfig {
	
}
