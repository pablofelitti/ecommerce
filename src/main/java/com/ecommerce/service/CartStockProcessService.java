package com.ecommerce.service;

import com.ecommerce.entity.Cart;

public interface CartStockProcessService {

    /**
     * Processes given cart in READY status by removing stock from each product and setting the cart status
     * to PROCESSED in case there is enough stock for every product, else the cart status is set to FAIL
     */
    Cart processCart(final Long cartId);
}
