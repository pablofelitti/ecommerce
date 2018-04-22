package com.ecommerce.service;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartProduct;
import com.ecommerce.entity.CartStatus;
import com.ecommerce.exception.ErrorCode;
import com.ecommerce.exception.MalformedRequestPayloadException;
import com.ecommerce.exception.ResourceDoesNotExistException;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
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
    public Cart processCart(final Long cartId) {

        if (cartId == null) {
            throw new MalformedRequestPayloadException(ErrorCode.CART_CANNOT_BE_EMPTY);
        }

        final Optional<Cart> cart = cartRepository.findById(cartId);

        if (!cart.isPresent()) {
            throw new ResourceDoesNotExistException(ErrorCode.CART_DOES_NOT_EXIST);
        }

        if (!CartStatus.READY.equals(cart.get().getStatus())) {
            throw new MalformedRequestPayloadException(ErrorCode.CART_STATUS_NOT_READY);
        }

        LOGGER.info("Processing cart id {}", cart.get().getId());

        final Predicate<CartProduct> hasNegativeStock = cartProduct ->
                cartProduct.getProduct().getStock() - cartProduct.getQuantity() < 0;

        boolean cartHasNegativeStock = cart.get().getCartProducts().stream().anyMatch(hasNegativeStock);

        if (cartHasNegativeStock) {
            updateCart(cart.get(), CartStatus.FAILED);
            LOGGER.info("Not enough stock to process cart id {}, setting status FAILED");
        } else {
            cart.get().getCartProducts().forEach(cartProduct -> {
                cartProduct.getProduct().removeFromStock(cartProduct.getQuantity());
                LOGGER.info("For cart {} removed {} items from stock of product id {}", cartId, cartProduct.getQuantity(), cartProduct.getProduct().getId());
                productRepository.save(cartProduct.getProduct());
            });
            updateCart(cart.get(), CartStatus.PROCESSED);
        }
        return cart.get();
    }

    private Cart updateCart(final Cart cart, final CartStatus status) {
        cart.setStatus(status);
        cartRepository.save(cart);
        return cart;
    }
}
