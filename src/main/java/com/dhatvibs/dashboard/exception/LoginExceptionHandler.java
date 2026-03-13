package com.dhatvibs.dashboard.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class LoginExceptionHandler {

    public static class LoginException extends RuntimeException {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private final HttpStatus status;

        public LoginException(String message, HttpStatus status) {
            super(message);
            this.status = status;
        }

        public HttpStatus getStatus() {
            return status;
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class ErrorResponse {

        private String message;
        private int status;
        private LocalDateTime timestamp;
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ErrorResponse> handleLoginException(LoginException ex) {

        ErrorResponse error = ErrorResponse.builder()
                .message(ex.getMessage())
                .status(ex.getStatus().value())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, ex.getStatus());
    }
}