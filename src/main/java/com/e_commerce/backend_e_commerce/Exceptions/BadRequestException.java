package com.e_commerce.backend_e_commerce.Exceptions;

public class BadRequestException extends RuntimeException {

	public BadRequestException(String message) {
		super(message);
	}
}
