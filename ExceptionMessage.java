package com.coderhop.rebound.exception.model;

import java.util.Date;

public class ExceptionMessage extends BaseExceptionMessage {

	private String appErrorCode;

	public String getAppErrorCode() {
		return appErrorCode;
	}

	public void setAppErrorCode(String appErrorCode) {
		this.appErrorCode = appErrorCode;
	}
	
	private String errorMessage;
	private String category;
	 private int httpErrorCode; 
	private String transactionId;
	private Date timestamp;
	

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getHttpErrorCode() {
		return httpErrorCode;
	}

	public void setHttpErrorCode(int httpErrorCode) {
		this.httpErrorCode = httpErrorCode;
	}




	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}


}
