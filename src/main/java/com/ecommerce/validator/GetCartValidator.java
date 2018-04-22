package com.ecommerce.validator;

import com.ecommerce.entity.Cart;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.validator.rule.CartExistsRule;
import org.springframework.stereotype.Component;

@Component
public class GetCartValidator {

    private final CartRepository cartRepository;

    GetCartValidator(final CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public CartValidationResult validate(final Long cartId) {
        final Cart cart = new CartExistsRule(cartRepository).validate(cartId);

        return new CartValidationResult(cart);
    }
}
