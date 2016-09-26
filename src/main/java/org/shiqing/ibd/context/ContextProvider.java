package org.shiqing.ibd.context;

/**
 * 
 * Context provider class which uses the {@link ThreadLocal} to 
 * hold the {@link Context} information. 
 * 
 * @author shiqing
 *
 */
public class ContextProvider {
	
	private static ThreadLocal<Context> CONTEXT = new ThreadLocal<Context>();
	
	public void establishOrUpdateContext(Context newContext) {
		// First remove the old context just in case
		CONTEXT.remove();
		
		CONTEXT.set(newContext);
	}
	
	public Context getContext() {
		return CONTEXT.get();
	}
	
	public void releaseContext() {
		CONTEXT.remove();
	}

}
