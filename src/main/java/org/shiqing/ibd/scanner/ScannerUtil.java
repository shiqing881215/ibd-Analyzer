package org.shiqing.ibd.scanner;

import org.shiqing.ibd.config.ConfigFactory;
import org.shiqing.ibd.context.Context;

public class ScannerUtil {

	public static void updateContext(String className) {
		Context context = ConfigFactory.get().getContextProvider().getContext();
		context.setScannerName(className);
		ConfigFactory.get().getContextProvider().establishOrUpdateContext(context);
	}
}
