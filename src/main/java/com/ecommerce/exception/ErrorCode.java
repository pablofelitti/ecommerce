package com.ecommerce.exception;

/**
 * Holds all of the different types of error messages
 */
public enum ErrorCode {

    DEFAULT("ecommerce_unexpected", "An unexpected error has occured"),
    FULLNAME_CANNOT_BE_EMPTY("ecommerce_cart_001", "Fullname cannot be empty"),
    EMAIL_CANNOT_BE_EMPTY("ecommerce_cart_002", "Email cannot be empty"),
    EMAIL_FORMAT_INCORRECT("ecommerce_cart_003", "Email format is not correct"),
    PRODUCT_CANNOT_BE_EMPTY("ecommerce_cart_004", "Product cannot be empty"),
    PRODUCT_QUANTITY_CANNOT_BE_EMPTY("ecommerce_cart_005", "Product quantity cannot be empty"),
    PRODUCT_QUANTITY_NEEDS_TO_BE_POSITIVE("ecommerce_cart_005", "Product quantity needs to be at least 1");

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
