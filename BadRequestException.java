package com.coderhop.rebound.exception.model;

public class BadRequestException extends RuntimeException{

	/**
	 * 
	 */
	private String appErrorCode;
	private static final long serialVersionUID = 1L;
	public BadRequestException(String message) {
		super(message);
	}
	
	public String getAppErrorCode() {
		return appErrorCode;
	}
	public void setAppErrorCode(String appErrorCode) {
		this.appErrorCode = appErrorCode;
	}
	
	public BadRequestException(String message,String appErrorCode) {
		super(message);
		this.appErrorCode=appErrorCode;
	}
}
