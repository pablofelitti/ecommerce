package com.ecommerce.dto;

public class AddCartProductDTO {

    private final Long productId;
    private final Integer quantity;

    public AddCartProductDTO(final Long productId, final Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
