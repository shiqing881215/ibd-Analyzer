package org.shiqing.ibd.filter;

import org.shiqing.ibd.config.ConfigFactory;
import org.shiqing.ibd.context.Context;

public class FilterUtil {

	public static void updateContext(String className) {
		Context context = ConfigFactory.get().getContextProvider().getContext();
		context.addFilterName(className);
		ConfigFactory.get().getContextProvider().establishOrUpdateContext(context);
	}
}
