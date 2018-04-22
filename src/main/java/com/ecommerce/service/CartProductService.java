package com.ecommerce.service;

import com.ecommerce.dto.AddCartProductDTO;
import com.ecommerce.dto.CartProductDTO;

import java.util.List;

public interface CartProductService {

    /**
     * Adds product to cart saving the current price at the moment the user adds it in case the price of the product
     * changes. If the product already exists it will update the quantity keeping the same unit price. Total price
     * in the cart is updated too
     *
     * @param cartId            Id of the cart where to create product
     * @param addCartProductDTO input from the body request
     * @return Added cart product
     */
    CartProductDTO addProductToCart(final Long cartId, final AddCartProductDTO addCartProductDTO);

    /**
     * Deletes product from the cart
     *
     * @param cartId    Id of the cart where to delete the product from
     * @param productId Id of the product to delete from the cart
     */
    void deleteProductFromCart(final Long cartId, final Long productId);

    /**
     * Returns the information of the list of products in the given cart cart
     *
     * @param cartId Id of the cart to get the products from
     * @return Information of the list of products from the cart
     */
    List<CartProductDTO> getCartProducts(final Long cartId);
}
