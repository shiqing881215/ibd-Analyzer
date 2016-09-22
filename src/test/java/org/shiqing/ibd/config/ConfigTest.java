package org.shiqing.ibd.config;

import org.junit.Test;

import junit.framework.TestCase;

public class ConfigTest extends TestCase {
	
	@Test
	public void testConfigFactory() throws Exception {
		assertEquals("/Users/Rossi/Documents/IBD/", ConfigFactory.get().getPropertiesProvider().getValue("path.root"));
		assertEquals("/Users/Rossi/Documents/IBD/results/", ConfigFactory.get().getPropertiesProvider().getValue("path.result"));
	}
}
