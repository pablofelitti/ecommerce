package com.ecommerce.validator;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.Product;

/**
 * Returns the cart and product that were retrieved during the validation process so that no further queries
 * are executed to return these entities
 */
public class CreateCartProductValidationResult {

    private final Cart cart;
    private final Product product;

    public CreateCartProductValidationResult(final Cart cart, final Product product) {
        this.cart = cart;
        this.product = product;
    }

    public Cart getCart() {
        return cart;
    }

    public Product getProduct() {
        return product;
    }
}
