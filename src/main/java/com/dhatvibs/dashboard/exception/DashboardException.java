package com.dhatvibs.dashboard.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

/*
 * Custom Exception
 */
public class DashboardException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final HttpStatus status;

    public DashboardException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    /*
     * Global Exception Handler
     */
    @RestControllerAdvice
    public static class DashboardExceptionHandler {

        // Handle DashboardException (custom exceptions)
        @ExceptionHandler(DashboardException.class)
        public ResponseEntity<ErrorResponse> handleDashboardException(DashboardException ex) {

            ErrorResponse error = ErrorResponse.builder()
                    .message(ex.getMessage())
                    .status(ex.getStatus().value())
                    .timestamp(LocalDateTime.now())
                    .build();

            return new ResponseEntity<>(error, ex.getStatus());
        }

        // Handle invalid ID format (/dashboards/abc)
        @ExceptionHandler(MethodArgumentTypeMismatchException.class)
        public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {

            ErrorResponse error = ErrorResponse.builder()
                    .message("Invalid dashboard ID. ID must be a number.")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .timestamp(LocalDateTime.now())
                    .build();

            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        // Handle unexpected exceptions
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {

            ErrorResponse error = ErrorResponse.builder()
                    .message("Internal server error. Please try again later.")
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .timestamp(LocalDateTime.now())
                    .build();

            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * Error Response DTO
     */
    @Data
    @Builder
    @AllArgsConstructor
    public static class ErrorResponse {

        private String message;
        private int status;
        private LocalDateTime timestamp;

    }
}