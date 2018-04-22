package com.ecommerce.validator.rule;

import com.ecommerce.entity.Cart;
import com.ecommerce.exception.ErrorCode;
import com.ecommerce.exception.ResourceDoesNotExistException;
import com.ecommerce.repository.CartRepository;

import java.util.Optional;

/**
 * Rule that validates if a given cart id exists, and returns it if present
 */
public class CartExistsRule implements ValidatorResponseRule<Long, Cart> {

    private final CartRepository cartRepository;

    public CartExistsRule(final CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart validate(final Long id) {
        final Optional<Cart> cart = cartRepository.findById(id);
        if (!cart.isPresent()) {
            throw new ResourceDoesNotExistException(ErrorCode.CART_DOES_NOT_EXIST);
        }
        return cart.get();
    }
}
