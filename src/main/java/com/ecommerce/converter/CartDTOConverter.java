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

    private CartProductDTOConverter cartProductDTOConverter;
    private DateUtils dateUtils;

    public CartDTOConverter(final CartProductDTOConverter cartProductDTOConverter,
                            final DateUtils dateUtils) {
        this.cartProductDTOConverter = cartProductDTOConverter;
        this.dateUtils = dateUtils;
    }

    @Override
    public CartDTO convert(final Cart cart) {

        String convertedCreationDate = dateUtils.convertToISOFormat(cart.getCreationDate());

        List<CartProductDTO> carts = cart.getCartProducts().stream().
                map(cartProductDTOConverter::convert).
                collect(Collectors.toList());

        return new CartDTO(cart.getId(), cart.getFullName(), cart.getEmail(), convertedCreationDate,
                carts, cart.getTotal(), cart.getStatus().toString());
    }
}
