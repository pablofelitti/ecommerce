package com.ecommerce.exception;

/**
 * Exception that indicates parameters received are not correct
 */
public class MalformedRequestPayloadException extends RuntimeException {

    private final ErrorCode errorCode;

    public MalformedRequestPayloadException(final ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
