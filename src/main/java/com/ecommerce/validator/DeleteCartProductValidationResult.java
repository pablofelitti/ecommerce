package com.ecommerce.validator;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartProduct;

/**
 * Returns the cart product that were retrieved during the validation process so that no further queries
 * are executed to return these entities
 */
public class DeleteCartProductValidationResult {

    private Cart cart;
    private CartProduct cartProduct;

    public DeleteCartProductValidationResult(Cart cart, final CartProduct cartProduct) {
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
