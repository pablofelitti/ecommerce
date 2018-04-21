package com.ecommerce.service;

public interface CartStockProcessService {

    /**
     * Processes carts in READY status by removing stock from each product and setting the cart status
     * to PROCESSED in case the process finishes successfully, else the cart status is set to FAIL
     */
    void processCart();
}
