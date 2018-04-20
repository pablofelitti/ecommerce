package com.ecommerce.dto;

public class AddCartProductDTO {

    private Long productId;
    private Integer quantity;

    public AddCartProductDTO(Long productId, Integer quantity) {
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
