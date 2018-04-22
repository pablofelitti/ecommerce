package com.ecommerce.job;

import com.ecommerce.entity.Cart;
import com.ecommerce.service.CartStockProcessService;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * Invokes the service to process the stock removal for a given cart
 */
@Component
public class AttemptProcessor implements ItemProcessor<Long, Cart> {

    private final CartStockProcessService cartStockProcessService;

    AttemptProcessor(final CartStockProcessService cartStockProcessService) {
        this.cartStockProcessService = cartStockProcessService;
    }

    @Override
    public Cart process(final Long cartId) {
        return cartStockProcessService.processCart(cartId);
    }
}
