package com.ecommerce.dto;

public class ErrorDetails {

    private final ErrorInfo error;

    public ErrorDetails(final ErrorInfo error) {
        this.error = error;
    }

    public ErrorInfo getError() {
        return error;
    }
}
