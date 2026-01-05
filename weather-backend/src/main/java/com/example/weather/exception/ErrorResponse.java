package com.example.weather.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@AllArgsConstructor
public class ErrorResponse {
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime timestamp;
	
    private int status;
    private String error;
    private String message;
    private String path;
    
    
}
