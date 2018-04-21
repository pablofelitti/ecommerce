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

    //TODO can we still make the validation approach better?

    CheckoutCartValidator(final CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public GetCartProductsValidationResult validate(final Long cartId) {
        Cart cart = new CartExistsRule(cartRepository).validate(cartId);
        new CartStatusRule(CartStatus.NEW, ErrorCode.CART_STATUS_NOT_NEW).validate(cart);
        new AnyProductExistsInCartRule(cart).validate();

        return new GetCartProductsValidationResult(cart);
    }
}
