package com.ecommerce.dto;

import java.math.BigDecimal;

public class ProductDTO {

    private Long id;
    private String description;
    private BigDecimal unitPrice;
    private Long stock;

    public ProductDTO(final Long id, final String description, final BigDecimal unitPrice, final Long stock) {
        this.id = id;
        this.description = description;
        this.unitPrice = unitPrice;
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public Long getStock() {
        return stock;
    }
}
