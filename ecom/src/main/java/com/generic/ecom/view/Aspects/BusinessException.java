package com.generic.ecom.view.Aspects;

public class BusinessException extends RuntimeException{
    /*
    This custom exception class is used to represent business logic errors in the application
    so when ever an error happend just throw this exception and ControllerAdvice(GlobalExceptionHandler class) will handle the return of message in proper format to the user back
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    private Integer status ; // this is http status
    private ErrorCode errorCode;
    private String message;

    public BusinessException(Integer status,ErrorCode errorCode, String message) {
        this.status=status;
        this.errorCode=errorCode;
        this.message=message;
    }

}
