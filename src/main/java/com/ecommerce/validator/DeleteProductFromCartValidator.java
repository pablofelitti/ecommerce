package com.ecommerce.validator;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartProduct;
import com.ecommerce.entity.CartStatus;
import com.ecommerce.exception.ErrorCode;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.validator.rule.CartExistsRule;
import com.ecommerce.validator.rule.CartStatusRule;
import com.ecommerce.validator.rule.ProductExistsInCartRule;
import org.springframework.stereotype.Component;

@Component
public class DeleteProductFromCartValidator {

    private final CartRepository cartRepository;

    DeleteProductFromCartValidator(final CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public DeleteCartProductValidationResult validate(Long cartId, Long productId) {

        final Cart cart = new CartExistsRule(cartRepository).validate(cartId);
        new CartStatusRule(CartStatus.NEW, ErrorCode.CART_STATUS_NOT_NEW).validate(cart);

        final CartProduct cartProduct = new ProductExistsInCartRule(cart).validate(productId);

        return new DeleteCartProductValidationResult(cart, cartProduct);
    }
}
