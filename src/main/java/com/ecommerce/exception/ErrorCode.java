package com.ecommerce.exception;

public enum ErrorCode {

    FULLNAME_CANNOT_BE_EMPTY("ecommerce_cart_001", "Fullname cannot be empty"),
    EMAIL_CANNOT_BE_EMPTY("ecommerce_cart_002", "Fullname cannot be empty"),
    EMAIL_FORMAT_INCORRECT("ecommerce_cart_003", "Email format is not correct");

    private String code;
    private String message;

    ErrorCode(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
