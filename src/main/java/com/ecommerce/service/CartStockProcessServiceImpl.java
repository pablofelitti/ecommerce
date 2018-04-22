package com.ecommerce.service;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartProduct;
import com.ecommerce.entity.CartStatus;
import com.ecommerce.exception.ErrorCode;
import com.ecommerce.exception.MalformedRequestPayloadException;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Predicate;

@Service
public class CartStockProcessServiceImpl implements CartStockProcessService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartStockProcessServiceImpl.class);

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    CartStockProcessServiceImpl(final ProductRepository productRepository,
                                final CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Cart processCart(final Cart cart) {
        if (cart == null) {
            throw new MalformedRequestPayloadException(ErrorCode.CART_CANNOT_BE_EMPTY);
        }

        if (!CartStatus.READY.equals(cart.getStatus())) {
            throw new MalformedRequestPayloadException(ErrorCode.CART_STATUS_NOT_READY);
        }

        LOGGER.info("Processing cart id {}", cart.getId());

        final Predicate<CartProduct> hasNegativeStock = cartProduct ->
                cartProduct.getProduct().getStock() - cartProduct.getQuantity() < 0;

        boolean cartHasNegativeStock = cart.getCartProducts().stream().anyMatch(hasNegativeStock);

        if (cartHasNegativeStock) {
            updateCart(cart, "Not enough stock to process cart id {}", CartStatus.FAILED);
        } else {
            cart.getCartProducts().forEach(cartProduct -> {
                cartProduct.getProduct().removeFromStock(cartProduct.getQuantity());
                LOGGER.info("Removed {} items from stock of product id {}", cartProduct.getQuantity(), cartProduct.getProduct().getId());
                productRepository.save(cartProduct.getProduct());
            });
            updateCart(cart, "Cart id {} processed successfully", CartStatus.PROCESSED);
        }
        return cart;
    }

    private Cart updateCart(final Cart cart, final String message, final CartStatus status) {
        LOGGER.error(message, cart.getId());
        cart.setStatus(status);
        cartRepository.save(cart);
        return cart;
    }
}
