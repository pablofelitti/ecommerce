package com.ecommerce.validator.rule;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartProduct;
import com.ecommerce.exception.ErrorCode;
import com.ecommerce.exception.ResourceDoesNotExistException;

import java.util.Optional;

/**
 * Rule that validates if any product exists in the cart
 */
public class AnyProductExistsInCartRule implements ValidatorRule<Cart> {

    @Override
    public void validate(final Cart cart) {

        final Optional<CartProduct> cartProduct = cart.getCartProducts().stream().findAny();

        if (!cartProduct.isPresent()) {
            throw new ResourceDoesNotExistException(ErrorCode.NO_PRODUCT_EXISTS_IN_CART);
        }
    }
}
