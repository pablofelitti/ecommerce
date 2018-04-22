package com.ecommerce.service;

import com.ecommerce.converter.CartDTOConverter;
import com.ecommerce.dto.CartCreationDTO;
import com.ecommerce.dto.CartDTO;
import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartStatus;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.validator.CheckoutCartValidator;
import com.ecommerce.validator.CreateCartValidator;
import com.ecommerce.validator.CartValidationResult;
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
    private final CheckoutCartValidator checkoutCartValidator;

    @Autowired
    public CartServiceImpl(final CartRepository cartRepository,
                           final CartDTOConverter cartDTOConverter,
                           final CreateCartValidator createCartValidator,
                           final GetCartValidator getCartValidator,
                           final CheckoutCartValidator checkoutCartValidator) {
        this.cartRepository = cartRepository;
        this.cartDTOConverter = cartDTOConverter;
        this.createCartValidator = createCartValidator;
        this.getCartValidator = getCartValidator;
        this.checkoutCartValidator = checkoutCartValidator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CartDTO create(final CartCreationDTO cartCreationDTO) {
        createCartValidator.validate(cartCreationDTO);

        final Cart newCart = createCartEntity(cartCreationDTO);

        final Cart savedCart = cartRepository.save(newCart);

        return cartDTOConverter.convert(savedCart);
    }

    @Override
    public CartDTO getCart(final Long cartId) {
        final CartValidationResult result = getCartValidator.validate(cartId);
        return cartDTOConverter.convert(result.getCart());
    }

    private Cart createCartEntity(final CartCreationDTO cartCreationDTO) {
        final Cart newCart = new Cart();
        newCart.setFullName(cartCreationDTO.getFullName());
        newCart.setEmail(cartCreationDTO.getEmail());
        newCart.setTotal(BigDecimal.ZERO);
        newCart.setStatus(CartStatus.NEW);
        newCart.setCreationDate(new Date());
        return newCart;
    }

    @Override
    public void checkoutCart(final Long cartId) {
        CartValidationResult validatorResult = checkoutCartValidator.validate(cartId);
        validatorResult.getCart().setStatus(CartStatus.READY);
        cartRepository.save(validatorResult.getCart());
    }

}
