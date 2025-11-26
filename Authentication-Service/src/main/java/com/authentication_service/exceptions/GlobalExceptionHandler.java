package com.authentication_service.exceptions;

import com.authentication_service.dtos.ApiError;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiError> handleUsernameNotFoundException(Exception e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND.value())
                .body(
                   new ApiError(
                           e.getMessage() ,
                           LocalDateTime.now(),
                           HttpStatus.NOT_FOUND
                   )
                ) ;
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolationException(Exception e){
        return ResponseEntity
                .status(HttpStatus.CONFLICT.value())
                .body(
                        new ApiError(
                                e.getMessage() ,
                                LocalDateTime.now(),
                                HttpStatus.CONFLICT
                        )
                ) ;
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFoundException(Exception e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND.value())
                .body(
                        new ApiError(
                                e.getMessage() ,
                                LocalDateTime.now(),
                                HttpStatus.NOT_FOUND
                        )
                ) ;
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception e){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        new ApiError(
                                e.getMessage() ,
                                LocalDateTime.now(),
                                HttpStatus.INTERNAL_SERVER_ERROR
                        )
                ) ;
    }

}
