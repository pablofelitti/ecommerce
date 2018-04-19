package com.ecommerce.dto;

public class ErrorInfo {

    private String code;
    private String message;

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