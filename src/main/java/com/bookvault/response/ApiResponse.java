package com.bookvault.response;

import java.time.LocalDateTime;

public class ApiResponse<T> {

	  private T data;
	    private String error;
	    private LocalDateTime timestamp;

	    public ApiResponse(T data, String error) {
	        this.data = data;
	        this.error = error;
	        this.timestamp = LocalDateTime.now();
	    }

		public T getData() {
			return data;
		}

		public void setData(T data) {
			this.data = data;
		}

		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
		}

		public LocalDateTime getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(LocalDateTime timestamp) {
			this.timestamp = timestamp;
		}
	    
	    
}
