package org.shiqing.ibd.exception;

import org.shiqing.ibd.services.QuoteService;

/**
 * Exception that is related to {@link QuoteService}
 * 
 * @author shiqing
 *
 */
public class QuoteException extends Exception {

	private static final long serialVersionUID = 1L;

	public QuoteException(Exception e) {
		super(e);
	}

}
