package com.ecommerce.validator.rule;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartProduct;
import com.ecommerce.exception.ErrorCode;
import com.ecommerce.exception.ResourceDoesNotExistException;

import java.util.Optional;

public class AnyProductExistsInCartRule implements ValidatorResponseSimpleRule {

    private final Cart cart;

    public AnyProductExistsInCartRule(final Cart cart) {
        this.cart = cart;
    }

    @Override
    public void validate() {

        Optional<CartProduct> cartProduct = cart.getCartProducts().stream().findAny();

        if (!cartProduct.isPresent()) {
            throw new ResourceDoesNotExistException(ErrorCode.NO_PRODUCT_EXISTS_IN_CART);
        }
    }
}
