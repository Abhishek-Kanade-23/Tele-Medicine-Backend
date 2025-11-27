package com.authentication_service.dtos;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ApiError {

    private String error ;
    private LocalDateTime localDateTime ;
    private HttpStatus httpStatus ;

    public ApiError() {
    }

    public ApiError(String error, LocalDateTime localDateTime, HttpStatus httpStatus) {
        this.error = error;
        this.localDateTime = localDateTime;
        this.httpStatus = httpStatus;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String toString() {
        return "ApiError{" +
                "error='" + error + '\'' +
                ", localDateTime=" + localDateTime +
                ", httpStatus=" + httpStatus +
                '}';
    }
}
