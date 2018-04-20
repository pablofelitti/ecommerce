package com.ecommerce.validator.rule;

import com.ecommerce.entity.Cart;
import com.ecommerce.exception.ErrorCode;
import com.ecommerce.exception.ResourceDoesNotExistException;
import com.ecommerce.repository.CartRepository;

import java.util.Optional;

public class CartExistsRule implements ValidatorResponseRule<Long, Cart> {

    private final CartRepository cartRepository;

    public CartExistsRule(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart validate(Long id) {
        Optional<Cart> cart = cartRepository.findById(id);
        if (!cart.isPresent()) {
            throw new ResourceDoesNotExistException(ErrorCode.CART_DOES_NOT_EXIST);
        }
        return cart.get();
    }
}
