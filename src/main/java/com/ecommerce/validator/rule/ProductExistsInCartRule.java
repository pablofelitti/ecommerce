package com.ecommerce.validator.rule;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartProduct;
import com.ecommerce.exception.ErrorCode;
import com.ecommerce.exception.MalformedRequestPayloadException;
import com.ecommerce.exception.ResourceDoesNotExistException;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * Rule that validates if the given product id exists in the cart. If present, the cart product is returned
 */
public class ProductExistsInCartRule implements ValidatorResponseRule<Long, CartProduct> {

    private final Cart cart;

    public ProductExistsInCartRule(final Cart cart) {
        this.cart = cart;
    }

    @Override
    public CartProduct validate(final Long productId) {

        if (productId == null) {
            throw new MalformedRequestPayloadException(ErrorCode.PRODUCT_CANNOT_BE_EMPTY);
        }

        final Predicate<CartProduct> cartProductExists = cartProduct -> productId.equals(cartProduct.getProduct().getId());

        final Optional<CartProduct> cartProduct = cart.getCartProducts().stream().
                filter(cartProductExists).
                findFirst();

        if (!cartProduct.isPresent()) {
            throw new ResourceDoesNotExistException(ErrorCode.PRODUCT_DOES_NOT_EXIST_IN_CART);
        }

        return cartProduct.get();
    }
}
