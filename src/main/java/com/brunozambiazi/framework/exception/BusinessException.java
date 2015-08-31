
package com.brunozambiazi.framework.exception;


public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -841286950002884918L;

	
	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Throwable t) {
		super(message, t);
	}
	
}
