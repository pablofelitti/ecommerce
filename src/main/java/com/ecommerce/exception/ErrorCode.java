package com.ecommerce.exception;

/**
 * Holds all of the different types of error messages
 */
public enum ErrorCode {

    DEFAULT("ecommerce_unexpected", "An unexpected error has occured"),
    MALFORMED_JSON("ecommerce_malformed_json", "Could not deserialize json request body due to syntax issue or wrong data type"),
    FULLNAME_CANNOT_BE_EMPTY("ecommerce_cart_001", "Fullname cannot be empty"),
    EMAIL_CANNOT_BE_EMPTY("ecommerce_cart_002", "Email cannot be empty"),
    EMAIL_FORMAT_INCORRECT("ecommerce_cart_003", "Email format is not correct"),
    PRODUCT_CANNOT_BE_EMPTY("ecommerce_cart_004", "Product cannot be empty"),
    PRODUCT_QUANTITY_CANNOT_BE_EMPTY("ecommerce_cart_005", "Product quantity cannot be empty"),
    CART_DOES_NOT_EXIST("ecommerce_cart_006", "Cart does not exist"),
    PRODUCT_DOES_NOT_EXIST("ecommerce_cart_007", "Product does not exist"),
    CART_PRODUCT_QUANTITY_MUST_BE_POSITIVE("ecommerce_cart_008", "The product quantity in the cart must be positive"),
    CART_STATUS_NOT_NEW("ecommerce_cart_009", "Cart status is not NEW"),
    PRODUCT_DOES_NOT_EXIST_IN_CART("ecommerce_cart_010", "Product does not exist in cart"),
    NO_PRODUCT_EXISTS_IN_CART("ecommerce_cart_011", "No product exists in cart"),
    CART_CANNOT_BE_EMPTY("ecommerce_cart_012", "Cart cannot be empty"),
    CART_STATUS_NOT_READY("ecommerce_cart_013", "Cart status is not READY");

    private final String code;
    private final String message;

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
