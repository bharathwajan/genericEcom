package com.generic.ecom.view.Aspects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // Expection handling using Aspects example , now anywhere in the application  we can simple throw an exception and it will be handled by this

    public static class ApiErrorResponse {
        private int status;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        private String errorCode;
        private String message;

        public ApiErrorResponse(int status, String errorCode, String message) {
            this.status = status;
            this.errorCode = errorCode;
            this.message = message;
        }
        // constructor, getters, setters
    }
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(BusinessException ex) {
        ApiErrorResponse error = new ApiErrorResponse(ex.getStatus(), ex.getErrorCode().toString(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.valueOf(ex.getStatus())); // dynamic error codes as well
    }
}
