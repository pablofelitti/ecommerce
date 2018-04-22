package com.ecommerce.dto;

import java.math.BigDecimal;

public class ProductDTO {

    private final Long id;
    private final String description;
    private final BigDecimal unitPrice;

    public ProductDTO(final Long id, final String description, final BigDecimal unitPrice) {
        this.id = id;
        this.description = description;
        this.unitPrice = unitPrice;
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
}
