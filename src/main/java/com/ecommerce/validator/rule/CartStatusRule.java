package com.ecommerce.validator.rule;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartStatus;
import com.ecommerce.exception.ErrorCode;
import com.ecommerce.exception.MalformedRequestPayloadException;
import com.ecommerce.exception.ResourceDoesNotExistException;

public class CartStatusRule implements ValidatorRule<Cart> {

    private final CartStatus cartStatus;
    private final ErrorCode errorCode;

    public CartStatusRule(final CartStatus cartStatus, final ErrorCode errorCode) {
        this.cartStatus = cartStatus;
        this.errorCode = errorCode;
    }

    @Override
    public void validate(Cart cart) {
        if (!cartStatus.equals(cart.getStatus())) {
            throw new MalformedRequestPayloadException(errorCode);
        }
    }
}
