package com.generic.ecom.view.ExceptionHandling;
import com.generic.ecom.DTOs.HomePageControllerResponse;
import io.jsonwebtoken.JwtException;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionalHandler {
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<HomePageControllerResponse<String>> jwtExceptionHandler(){
        // Note : This exception handling wont work because Expired JWT token will be rejected from the filter chain
        // it wont even reach the controller .. lets try writting some other exception handlling
        HomePageControllerResponse<String> response = new HomePageControllerResponse<>(false ,"JWT token expired consider logging in again",null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<HomePageControllerResponse<String>> sqlExceptionHandler(PSQLException ex){
        // This is used when any sql exception happens --> Like key constraint error.
        HomePageControllerResponse<String> response = new HomePageControllerResponse<>(false ,"Error in executing the query",ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // Bad request is used when the input given violates the accepted input
    }

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
