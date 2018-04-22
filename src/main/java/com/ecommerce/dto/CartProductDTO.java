package com.ecommerce.dto;

import java.math.BigDecimal;

public class CartProductDTO {

    private final Long id;
    private final ProductDTO product;
    private final Integer quantity;
    private final BigDecimal unitPrice;

    public CartProductDTO(final Long id, final ProductDTO product,
                          final Integer quantity, final BigDecimal unitPrice) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Long getId() {
        return id;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
}
