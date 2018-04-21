package com.ecommerce.service;

import com.ecommerce.dto.CartCreationDTO;
import com.ecommerce.dto.CartDTO;

public interface CartService {

    /**
     * Creates and initializes a cart
     *
     * @param cartCreationDTO input from the body request
     * @return created cart information
     */
    CartDTO create(final CartCreationDTO cartCreationDTO);

    /**
     * Gets the cart
     *
     * @param cartId Id of the cart to update
     * @return Information of the cart
     */
    CartDTO getCart(final Long cartId);
}
