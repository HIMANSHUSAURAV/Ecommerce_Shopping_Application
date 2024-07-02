package com.jsp.ecommerce_application.utility;

public class ErrorStructure<T> {

	private int status;
	private String message;
	private T rootcause;
	
	public int getStatus() {
		return status;
	}
	public String getMessage() {
		return message;
	}
	public T getRootcause() {
		return rootcause;
	}
	public ErrorStructure<T> setStatus(int status) {
		this.status = status;
		return this;
	}
	public ErrorStructure<T> setMessage(String message) {
		this.message = message;
		return this;
	}
	public ErrorStructure<T> setRootcause(T rootcause) {
		this.rootcause = rootcause;
		return this;
	}

}
