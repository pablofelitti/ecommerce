package com.ecommerce.service;

import com.ecommerce.converter.CartDTOConverter;
import com.ecommerce.dto.CartCreationDTO;
import com.ecommerce.dto.CartDTO;
import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartStatus;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.validator.CartCreationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;
    private CartDTOConverter cartDTOConverter;
    private CartCreationValidator cartCreationValidator;

    @Autowired
    public CartServiceImpl(final CartRepository cartRepository,
                           final CartDTOConverter cartDTOConverter,
                           final CartCreationValidator cartCreationValidator) {
        this.cartRepository = cartRepository;
        this.cartDTOConverter = cartDTOConverter;
        this.cartCreationValidator = cartCreationValidator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CartDTO create(final CartCreationDTO cartCreationDTO) {
        cartCreationValidator.validate(cartCreationDTO);

        Cart newCart = createCartEntity(cartCreationDTO);

        Cart savedCart = cartRepository.save(newCart);

        return cartDTOConverter.convert(savedCart);
    }

    private Cart createCartEntity(CartCreationDTO cartCreationDTO) {
        Cart newCart = new Cart();
        newCart.setFullName(cartCreationDTO.getFullName());
        newCart.setEmail(cartCreationDTO.getEmail());
        newCart.setTotal(new BigDecimal(0));
        newCart.setStatus(CartStatus.NEW);
        newCart.setCreationDate(new Date());
        return newCart;
    }

}
