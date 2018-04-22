package com.ecommerce.validator;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartProduct;

/**
 * Returns the cart and cart product that were retrieved during the validation process so that no further queries
 * are executed to return these entities
 */
public class DeleteCartProductValidationResult {

    private final Cart cart;
    private final CartProduct cartProduct;

    public DeleteCartProductValidationResult(final Cart cart, final CartProduct cartProduct) {
        this.cart = cart;
        this.cartProduct = cartProduct;
    }

    public CartProduct getCartProduct() {
        return cartProduct;
    }

    public Cart getCart() {
        return cart;
    }
}
