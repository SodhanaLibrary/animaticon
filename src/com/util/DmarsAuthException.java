package com.util;

public class DmarsAuthException extends Exception {

	private static final long serialVersionUID = 1L;

	private String message;

	public DmarsAuthException(String string) {
		message = string;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}			
	
