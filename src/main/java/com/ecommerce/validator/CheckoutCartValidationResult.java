package com.ecommerce.validator;

import com.ecommerce.entity.Cart;

/**
 * Returns the cart product that was retrieved during the validation process so that no further queries
 * are executed to return this entity
 */
public class CheckoutCartValidationResult {

    private final Cart cart;

    public CheckoutCartValidationResult(final Cart cart) {
        this.cart = cart;
    }

    public Cart getCart() {
        return cart;
    }
}
