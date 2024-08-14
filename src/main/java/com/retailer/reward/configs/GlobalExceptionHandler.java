package com.retailer.reward.configs;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.retailer.reward.handlers.ResponseHandler;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<?> printGlobalException(Exception ex) {
		StringBuilder message = new StringBuilder("Internal Server Error: ");
		message.append(ex.getMessage());
		log.error("Internal Server Error : ", ex.getMessage(), ex);
		return ResponseHandler.createResponse(message.toString(), HttpStatus.INTERNAL_SERVER_ERROR, null);
	}

	@ExceptionHandler(value = RuntimeException.class)
	public ResponseEntity<?> printRuntimeExceptions(RuntimeException ex) {
		StringBuilder message = new StringBuilder("RuntimeException Occurred :: ");
		message.append(ex.getMessage());
		log.error("RuntimeException Occurred :: ", ex.getMessage(), ex);
		return ResponseHandler.createResponse(message.toString(), HttpStatus.BAD_REQUEST, null);

	}

}
