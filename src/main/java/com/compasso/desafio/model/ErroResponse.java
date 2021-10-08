package com.compasso.desafio.model;

public class ErroResponse {
	private int status_code;
	private String message;
	
	public ErroResponse(int status_code, String message) {
		this.setStatus_code(status_code);
		this.setMessage(message);
	}

	public int getStatus_code() {
		return status_code;
	}

	public void setStatus_code(int status_code) {
		this.status_code = status_code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
