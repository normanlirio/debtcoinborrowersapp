package com.debtcoin.debtcoinapp.API.model;

import java.util.Date;

public class ResultMessage<T> {
	private String timeStamp;
	private String responseCode;
	private T body;
	private String responseMessage;
	
	public ResultMessage(String responseCode,String responseMessage, T body) {
		super();
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
		this.timeStamp = new Date().toString();
		this.body = body;
	}
	
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setresponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.responseMessage = errorMessage;
	}
	
	
}
