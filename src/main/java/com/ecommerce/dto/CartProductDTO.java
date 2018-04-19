package com.ecommerce.dto;

import java.math.BigDecimal;

public class CartProductDTO {

    private Long id;
    private ProductDTO product;
    private Integer quantity;
    private BigDecimal unitPrice;

    public CartProductDTO(final Long id, final ProductDTO product, final Integer quantity, final BigDecimal unitPrice) {
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
