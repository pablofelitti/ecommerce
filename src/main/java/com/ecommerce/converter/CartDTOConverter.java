package com.ecommerce.converter;

import com.ecommerce.dto.CartDTO;
import com.ecommerce.entity.Cart;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class CartDTOConverter implements Converter<Cart, CartDTO> {

    private static final String ISO_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    @Override
    public CartDTO convert(final Cart cart) {

        SimpleDateFormat format = new SimpleDateFormat(ISO_DATETIME_FORMAT);
        String convertedCreationDate = format.format(cart.getCreationDate());

        //TODO we still need to convert remaining product DTO, currently not necessary for create API

        return new CartDTO(cart.getId(), cart.getFullName(), cart.getEmail(), convertedCreationDate,
                null, cart.getTotal(), cart.getStatus().toString());
    }
}
