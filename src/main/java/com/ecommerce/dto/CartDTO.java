package com.ecommerce.dto;

import java.math.BigDecimal;
import java.util.List;

public class CartDTO {

    private final Long id;
    private final String fullName;
    private final String email;
    private final String creationDate;
    private final List<CartProductDTO> cartProducts;
    private final BigDecimal total;
    private final String status;

    public CartDTO(final Long id, final String fullName, final String email, final String creationDate,
                   final List<CartProductDTO> cartProducts, final BigDecimal total, final String status) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.creationDate = creationDate;
        this.cartProducts = cartProducts;
        this.total = total;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public List<CartProductDTO> getCartProducts() {
        return cartProducts;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }
}
