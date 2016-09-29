package org.shiqing.ibd.enrich;

import org.shiqing.ibd.config.ConfigFactory;
import org.shiqing.ibd.context.Context;

public class EnricherUtil {
	
	public static void updateContext(String className) {
		Context context = ConfigFactory.get().getContextProvider().getContext();
		context.addEnricherName(className);
		ConfigFactory.get().getContextProvider().establishOrUpdateContext(context);
	}
}
