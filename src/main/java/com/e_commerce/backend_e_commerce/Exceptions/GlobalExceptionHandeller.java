package com.e_commerce.backend_e_commerce.Exceptions;

import java.net.http.HttpRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandeller {
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ErrorResponce> productNotFoundHandeller(ProductNotFoundException ex, WebRequest request) {
	    
	    ErrorResponce error = new ErrorResponce(
	        HttpStatus.NOT_FOUND.value(),          // 404
	        HttpStatus.NOT_FOUND.getReasonPhrase(), // "Not Found"
	        ex.getMessage(),                        // The custom message from exception
	        request.getDescription(false).replace("uri=", "")  // Cleans the request URI
	    ); 
	    
	    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}


}
