package com.ecommerce.converter;

import com.ecommerce.dto.CartCreationDTO;
import com.ecommerce.entity.Cart;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CartCreationDTOConverter implements Converter<CartCreationDTO, Cart> {

    @Override
    public Cart convert(CartCreationDTO cartCreationDTO) {
        Cart cart = new Cart();

        cart.setFullName(cartCreationDTO.getFullName());
        cart.setEmail(cartCreationDTO.getEmail());

        return cart;
    }
}
