package com.ecommerce.service;

import com.ecommerce.dto.AddCartProductDTO;
import com.ecommerce.dto.CartProductDTO;

public interface CartProductService {

    /**
     * Adds product to cart saving the current price at the moment the user adds it in case the price of the product
     * changes
     *
     * @param cartId            Id of the cart where to create product
     * @param addCartProductDTO input from the body request
     * @return created
     */
    CartProductDTO addProductToCart(Long cartId, AddCartProductDTO addCartProductDTO);
}
