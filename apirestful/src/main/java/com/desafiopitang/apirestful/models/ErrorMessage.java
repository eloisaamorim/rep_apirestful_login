package com.desafiopitang.apirestful.models;

public class ErrorMessage {
	private String message;

	private int errorCode;

	public String getMessage() {
		return message;
	}

	public ErrorMessage(String message,int errorCode ) {
		this.message = message;
		this.errorCode = errorCode;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public String toString() {
		return "ErrorMessage [message=" + message + ", errorCode=" + errorCode + "]";
	}
	
	
	
}
