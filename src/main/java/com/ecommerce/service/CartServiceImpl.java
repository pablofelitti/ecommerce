package com.ecommerce.service;

import com.ecommerce.converter.CartCreationDTOConverter;
import com.ecommerce.converter.CartDTOConverter;
import com.ecommerce.dto.CartCreationDTO;
import com.ecommerce.dto.CartDTO;
import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartStatus;
import com.ecommerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;
    private CartCreationDTOConverter cartCreationDTOConverter;
    private CartDTOConverter cartDTOConverter;

    @Autowired
    public CartServiceImpl(final CartRepository cartRepository,
                           final CartCreationDTOConverter cartCreationDTOConverter,
                           final CartDTOConverter cartDTOConverter) {
        this.cartRepository = cartRepository;
        this.cartCreationDTOConverter = cartCreationDTOConverter;
        this.cartDTOConverter = cartDTOConverter;
    }

    @Override
    public CartDTO create(CartCreationDTO cartCreationDTO) {
        Cart newCart = cartCreationDTOConverter.convert(cartCreationDTO);

        //TODO validations are still missing

        initializeNewCart(newCart);

        Cart savedCart = cartRepository.save(newCart);

        return cartDTOConverter.convert(savedCart);
    }

    private void initializeNewCart(Cart newCart) {
        newCart.setTotal(new BigDecimal(0));
        newCart.setStatus(CartStatus.NEW);
        newCart.setCreationDate(new Date());
    }
}
