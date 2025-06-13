package com.e_commerce.backend_e_commerce.Exceptions;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class ErrorResponce {
	private long status;
	
	private String error;
	
	private String message;
	
	private LocalDateTime timeStamp;
	
	private String path;

	public ErrorResponce(long status, String error, String message, String path) {
		super();
		this.status = status;
		this.error = error;
		this.message = message;
		this.path = path;
	}
	
	

}
