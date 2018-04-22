package com.ecommerce.exception;

/**
 * Exception that indicates that requested resource does not exist
 */
public class ResourceDoesNotExistException extends RuntimeException {

    private final ErrorCode errorCode;

    public ResourceDoesNotExistException(final ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
