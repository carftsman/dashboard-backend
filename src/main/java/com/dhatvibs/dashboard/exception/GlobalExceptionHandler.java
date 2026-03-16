package com.dhatvibs.dashboard.exception;

<<<<<<< HEAD
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
=======
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
>>>>>>> c35f69c65ed7288325d8e423c4940a6c0e61612c

@RestControllerAdvice
public class GlobalExceptionHandler {

<<<<<<< HEAD
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex){

        return ResponseEntity.internalServerError().body(
                Map.of(
                        "error", ex.getMessage(),
                        "timestamp", LocalDateTime.now()
                )
        );
    }
=======
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailExists(EmailAlreadyExistsException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

>>>>>>> c35f69c65ed7288325d8e423c4940a6c0e61612c
}