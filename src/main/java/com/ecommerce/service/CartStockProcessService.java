package com.ecommerce.service;

import com.ecommerce.entity.Cart;

public interface CartStockProcessService {

    /**
     * Processes given cart in READY status by removing stock from each product and setting the cart status
     * to PROCESSED in case the process finishes successfully, else the cart status is set to FAIL
     */
    void processCart(final Cart cart);
}
