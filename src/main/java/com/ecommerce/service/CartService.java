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
    CartDTO create(CartCreationDTO cartCreationDTO);

    /**
     * Updates the total cart
     *
     * @param cartId Id of the cart to update
     */
    void updateTotalCart(Long cartId);
}
