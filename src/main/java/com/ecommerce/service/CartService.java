package com.ecommerce.service;

import com.ecommerce.dto.AddCartProductDTO;
import com.ecommerce.dto.CartCreationDTO;
import com.ecommerce.dto.CartDTO;
import com.ecommerce.dto.CartProductDTO;

public interface CartService {

    /**
     * Creates and initializes a cart
     *
     * @param cartCreationDTO input from the body request
     * @return created cart information
     */
    CartDTO create(CartCreationDTO cartCreationDTO);

    /**
     * Adds product to cart saving the current price at the moment the user adds it in case the price of the product
     * changes
     *
     * @param addCartProductDTO input from the body request
     * @return created
     */
    CartProductDTO addProductToCart(AddCartProductDTO addCartProductDTO);
}
