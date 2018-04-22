package com.ecommerce.dto;

public class CartCreationDTO {

    private final String fullName;
    private final String email;

    public CartCreationDTO(final String fullName, final String email) {
        this.fullName = fullName;
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }
}
