package com.ecommerce.validator;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartProduct;
import com.ecommerce.entity.CartStatus;
import com.ecommerce.exception.ErrorCode;
import com.ecommerce.repository.CartProductRepository;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.validator.rule.CartExistsRule;
import com.ecommerce.validator.rule.CartStatusRule;
import com.ecommerce.validator.rule.ProductExistsInCartRule;
import org.springframework.stereotype.Component;

@Component
public class DeleteProductFromCartValidator {

    private CartRepository cartRepository;
    private CartProductRepository cartProductRepository;

    DeleteProductFromCartValidator(final CartRepository cartRepository,
                                   final CartProductRepository cartProductRepository) {
        this.cartRepository = cartRepository;
        this.cartProductRepository = cartProductRepository;
    }

    //TODO can we still make the validation approach better?

    public DeleteCartProductValidationResult validate(Long cartId, Long productId) {

        Cart cart = new CartExistsRule(cartRepository).validate(cartId);
        new CartStatusRule(CartStatus.NEW, ErrorCode.CART_STATUS_NOT_NEW).validate(cart);

        CartProduct cartProduct = new ProductExistsInCartRule(cart).validate(productId);

        return new DeleteCartProductValidationResult(cart, cartProduct);
    }
}
