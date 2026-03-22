package com.bookvault.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntime(RuntimeException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("data", null);
        response.put("error", ex.getMessage());
        response.put("timestamp", LocalDateTime.now());

        return ResponseEntity.badRequest().body(response);
    }
	
	 @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {

	        String errors = ex.getBindingResult()
	                .getFieldErrors()
	                .stream()
	                .map(err -> err.getField() + ": " + err.getDefaultMessage())
	                .collect(Collectors.joining(", "));

	        Map<String, Object> response = new HashMap<>();
	        response.put("data", null);
	        response.put("error", errors);
	        response.put("timestamp", LocalDateTime.now());

	        return ResponseEntity.badRequest().body(response);
	    }
}
