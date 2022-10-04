package com.coderhop.rebound.exception.model;

public class SystemException  extends RuntimeException{

	/**
	 * 
	 */
	
	private String appErrorCode;
	
	
	private static final long serialVersionUID = 1L;
	public SystemException(String message) {
		super(message);
	}
	
	public SystemException(String message,String appErrorCode) {
		super(message);
		this.appErrorCode=appErrorCode;
	}

	public String getAppErrorCode() {
		return appErrorCode;
	}

	public void setAppErrorCode(String appErrorCode) {
		this.appErrorCode = appErrorCode;
	}
	

}