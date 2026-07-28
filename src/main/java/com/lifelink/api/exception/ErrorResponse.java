package com.lifelink.api.exception;

public class ErrorResponse {
    private ErrorCode code;
    private String message;

    public ErrorResponse(ErrorCode code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorCode getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
    
}
