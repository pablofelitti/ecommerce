package com.ecommerce.exception;

public class ResourceDoesNotExistException extends RuntimeException {

    private ErrorCode errorCode;

    public ResourceDoesNotExistException(final ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
