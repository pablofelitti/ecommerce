package com.ecommerce.dto;

public class ErrorInfo {

    private final String code;
    private final String message;

    public ErrorInfo(final String code, final String message) {
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