package com.ecommerce.entity;

public enum CartStatus {

    /**
     * Cart is still being processed by the customer
     */
    PENDING,

    /**
     * Payment has been succesfully processed
     */
    READY,

    /**
     * Stock has been enough to fulfill cart purchase
     */
    PROCESSED,

    /**
     * Not enough stock for a product of the cart
     */
    FAILED
}
