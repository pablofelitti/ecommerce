package com.ecommerce.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "CART_PRODUCT", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"CART_ID", "PRODUCT_ID"})
})
public class CartProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "CART_ID")
    private Cart cart;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "UNIT_PRICE")
    private BigDecimal unitPrice;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public BigDecimal getTotal() {
        return unitPrice.multiply(new BigDecimal(quantity));
    }
}
