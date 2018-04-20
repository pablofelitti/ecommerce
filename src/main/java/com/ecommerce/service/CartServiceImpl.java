package com.ecommerce.service;

import com.ecommerce.converter.CartDTOConverter;
import com.ecommerce.dto.CartCreationDTO;
import com.ecommerce.dto.CartDTO;
import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartStatus;
import com.ecommerce.exception.ErrorCode;
import com.ecommerce.exception.MalformedRequestPayloadException;
import com.ecommerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CartServiceImpl implements CartService {

    private static final Pattern emailPattern = Pattern.compile("^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$");

    private CartRepository cartRepository;
    private CartDTOConverter cartDTOConverter;

    @Autowired
    public CartServiceImpl(final CartRepository cartRepository,
                           final CartDTOConverter cartDTOConverter) {
        this.cartRepository = cartRepository;
        this.cartDTOConverter = cartDTOConverter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CartDTO create(final CartCreationDTO cartCreationDTO) {
        //TODO this validations should be refactored
        validateParameters(cartCreationDTO);

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

    private void validateParameters(final CartCreationDTO cartCreationDTO) {
        if (StringUtils.isEmpty(cartCreationDTO.getFullName())) {
            throw new MalformedRequestPayloadException(ErrorCode.FULLNAME_CANNOT_BE_EMPTY);
        }

        if (StringUtils.isEmpty(cartCreationDTO.getEmail())) {
            throw new MalformedRequestPayloadException(ErrorCode.EMAIL_CANNOT_BE_EMPTY);
        }

        Matcher matcher = emailPattern.matcher(cartCreationDTO.getEmail());
        if (!matcher.matches()) {
            throw new MalformedRequestPayloadException(ErrorCode.EMAIL_FORMAT_INCORRECT);
        }
    }
}
