package com.ecommerce.converter;

import com.ecommerce.dto.CartDTO;
import com.ecommerce.dto.CartProductDTO;
import com.ecommerce.entity.Cart;
import com.ecommerce.utils.DateUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartDTOConverter implements Converter<Cart, CartDTO> {

    private final CartProductDTOConverter cartProductDTOConverter;
    private final DateUtils dateUtils;

    public CartDTOConverter(final CartProductDTOConverter cartProductDTOConverter,
                            final DateUtils dateUtils) {
        this.cartProductDTOConverter = cartProductDTOConverter;
        this.dateUtils = dateUtils;
    }

    @Override
    public CartDTO convert(final Cart cart) {

        final String convertedCreationDate = dateUtils.convertToISOFormat(cart.getCreationDate());
        List<CartProductDTO> carts = null;

        //TODO is there something in stream api for these cases?
        if (cart.getCartProducts() != null) {
            carts = cart.getCartProducts().stream().
                map(cartProductDTOConverter::convert).
                collect(Collectors.toList());
        }

        return new CartDTO(cart.getId(), cart.getFullName(), cart.getEmail(), convertedCreationDate,
                carts, cart.getTotal(), cart.getStatus().toString());
    }
}
