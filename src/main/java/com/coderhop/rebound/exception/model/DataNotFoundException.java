package com.coderhop.rebound.exception.model;

public class DataNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private String appErrorCode;
	private static final long serialVersionUID = 1L;
	public DataNotFoundException(String message) {
		super(message);
	}
	
	public String getAppErrorCode() {
		return appErrorCode;
	}
	public void setAppErrorCode(String appErrorCode) {
		this.appErrorCode = appErrorCode;
	}
	
	public DataNotFoundException(String message,String appErrorCode) {
		super(message);
		this.appErrorCode=appErrorCode;
	}

}
