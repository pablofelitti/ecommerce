package com.ecommerce.exception;

public class MalformedRequestPayloadException extends RuntimeException {

    private ErrorCode errorCode;

    public MalformedRequestPayloadException(final ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
