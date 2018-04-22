package com.ecommerce.validator;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartStatus;
import com.ecommerce.exception.ErrorCode;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.validator.rule.AnyProductExistsInCartRule;
import com.ecommerce.validator.rule.CartExistsRule;
import com.ecommerce.validator.rule.CartStatusRule;
import org.springframework.stereotype.Component;

@Component
public class CheckoutCartValidator {

    private CartRepository cartRepository;

    CheckoutCartValidator(final CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public CartValidationResult validate(final Long cartId) {
        final Cart cart = new CartExistsRule(cartRepository).validate(cartId);
        new CartStatusRule(CartStatus.NEW, ErrorCode.CART_STATUS_NOT_NEW).validate(cart);
        new AnyProductExistsInCartRule().validate(cart);

        return new CartValidationResult(cart);
    }
}
