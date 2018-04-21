package com.ecommerce.service;

import com.ecommerce.converter.CartDTOConverter;
import com.ecommerce.dto.CartCreationDTO;
import com.ecommerce.dto.CartDTO;
import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartStatus;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.validator.CreateCartValidator;
import com.ecommerce.validator.GetCartProductsValidationResult;
import com.ecommerce.validator.GetCartValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartDTOConverter cartDTOConverter;
    private final CreateCartValidator createCartValidator;
    private final GetCartValidator getCartValidator;

    @Autowired
    public CartServiceImpl(final CartRepository cartRepository,
                           final CartDTOConverter cartDTOConverter,
                           final CreateCartValidator createCartValidator,
                           final GetCartValidator getCartValidator) {
        this.cartRepository = cartRepository;
        this.cartDTOConverter = cartDTOConverter;
        this.createCartValidator = createCartValidator;
        this.getCartValidator = getCartValidator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CartDTO create(final CartCreationDTO cartCreationDTO) {
        createCartValidator.validate(cartCreationDTO);

        Cart newCart = createCartEntity(cartCreationDTO);

        Cart savedCart = cartRepository.save(newCart);

        return cartDTOConverter.convert(savedCart);
    }

    @Override
    public CartDTO getCart(final Long cartId) {
        GetCartProductsValidationResult result = getCartValidator.validate(cartId);
        return cartDTOConverter.convert(result.getCart());
    }

    private Cart createCartEntity(final CartCreationDTO cartCreationDTO) {
        Cart newCart = new Cart();
        newCart.setFullName(cartCreationDTO.getFullName());
        newCart.setEmail(cartCreationDTO.getEmail());
        newCart.setTotal(new BigDecimal(0));
        newCart.setStatus(CartStatus.NEW);
        newCart.setCreationDate(new Date());
        return newCart;
    }

}
