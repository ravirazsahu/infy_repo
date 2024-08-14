package com.retailer.reward.handlers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {

	public static ResponseEntity<Object> createResponse(String message, HttpStatus status, Object data) {

		Map<String, Object> respmap = new HashMap<>();
		respmap.put("message", message);
		respmap.put("status", status.value());
		if (data != null)
			respmap.put("data", data);

		return new ResponseEntity<Object>(respmap, status);

	}

}
