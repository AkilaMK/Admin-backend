package com.example.admin.exception;

import com.example.admin.dto.response.ApiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handleUnauthorizedException(UnauthorizedException ex) {
        log.error("Unauthorized", ex);
        return getApiResponseResponseEntity(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException ex) {
        log.error("Bad Request", ex);
        return getApiResponseResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("Bad Request", ex);
        return getApiResponseResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<?> handleInternalServerException(InternalServerException ex) {
        log.error("Internal Server Error", ex);
        return getApiResponseResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to process the request.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception ex) {
        log.error("Internal Server Error", ex);
        return getApiResponseResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to process the request.");
    }

    private static ResponseEntity<ApiResponse> getApiResponseResponseEntity(HttpStatus status, String message) {
        ApiResponse response = new ApiResponse(status.value(), message);
        return ResponseEntity.status(status).body(response);
    }
}

