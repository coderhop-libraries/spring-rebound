package com.coderhop.rebound.exception.model;

public class ForbiddenException  extends RuntimeException{

	/**
	 * 
	 */
	private String appErrorCode;
	private static final long serialVersionUID = 1L;
	public ForbiddenException(String message) {
		super(message);
	}
	
	public String getAppErrorCode() {
		return appErrorCode;
	}
	public void setAppErrorCode(String appErrorCode) {
		this.appErrorCode = appErrorCode;
	}
	
	
	public ForbiddenException(String message,String appErrorCode) {
		super(message);
		this.appErrorCode=appErrorCode;
	}
}


