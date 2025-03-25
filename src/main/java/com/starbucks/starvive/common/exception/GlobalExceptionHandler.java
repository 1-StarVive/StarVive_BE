package com.starbucks.starvive.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        logger.error("Unhandled exception occurred: ", e);
        
        ErrorResponse response = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage() != null ? e.getMessage() : "서버 내부 오류가 발생했습니다.",
                e.getClass().getName()
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    
    public static class ErrorResponse {
        private int status;
        private String message;
        private String exceptionClass;
        
        public ErrorResponse(int status, String message, String exceptionClass) {
            this.status = status;
            this.message = message;
            this.exceptionClass = exceptionClass;
        }
        
        public int getStatus() {
            return status;
        }
        
        public String getMessage() {
            return message;
        }
        
        public String getExceptionClass() {
            return exceptionClass;
        }
    }
} 