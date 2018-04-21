package com.ecommerce.validator;

import com.ecommerce.entity.Cart;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.validator.rule.CartExistsRule;
import org.springframework.stereotype.Component;

@Component
public class GetCartValidator {

    private CartRepository cartRepository;

    //TODO can we still make the validation approach better?

    GetCartValidator(final CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public GetCartProductsValidationResult validate(final Long cartId) {
        Cart cart = new CartExistsRule(cartRepository).validate(cartId);

        return new GetCartProductsValidationResult(cart);
    }
}
