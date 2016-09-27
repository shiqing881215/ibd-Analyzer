package org.shiqing.ibd.analyzer;

import org.shiqing.ibd.config.ConfigFactory;
import org.shiqing.ibd.context.Context;

public class AnalyzerUtil {

	public static void updateContext(String className) {
		Context context = ConfigFactory.get().getContextProvider().getContext();
		context.setAnalyzerName(className);
		ConfigFactory.get().getContextProvider().establishOrUpdateContext(context);
	}
}
