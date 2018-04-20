package com.ecommerce.service;

import com.ecommerce.converter.CartProductDTOConverter;
import com.ecommerce.dto.AddCartProductDTO;
import com.ecommerce.dto.CartProductDTO;
import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartProduct;
import com.ecommerce.entity.Product;
import com.ecommerce.exception.ErrorCode;
import com.ecommerce.exception.MalformedRequestPayloadException;
import com.ecommerce.repository.CartProductRepository;
import com.ecommerce.validator.CartProductCreationValidator;
import com.ecommerce.validator.CartProductValidationResult;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartProductServiceImpl implements CartProductService {

    private CartProductRepository cartProductRepository;
    private CartProductDTOConverter cartProductDTOConverter;
    private CartProductCreationValidator cartProductCreationValidator;

    public CartProductServiceImpl(final CartProductRepository cartProductRepository,
                                  final CartProductDTOConverter cartProductDTOConverter,
                                  final CartProductCreationValidator cartProductCreationValidator) {
        this.cartProductRepository = cartProductRepository;
        this.cartProductDTOConverter = cartProductDTOConverter;
        this.cartProductCreationValidator = cartProductCreationValidator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CartProductDTO addProductToCart(Long cartId, final AddCartProductDTO addCartProductDTO) {
        CartProductValidationResult validationResult = cartProductCreationValidator.validate(cartId, addCartProductDTO);

        CartProduct newCartProduct = createCartProduct(addCartProductDTO.getQuantity(),
                validationResult.getCart(), validationResult.getProduct());

        CartProduct savedCartProduct = cartProductRepository.save(newCartProduct);

        //TODO we need to update cart total

        return cartProductDTOConverter.convert(savedCartProduct);
    }

    private CartProduct createCartProduct(final Integer quantity, final Cart cart, final Product product) {
        CartProduct newCartProduct = new CartProduct();
        newCartProduct.setQuantity(quantity);
        newCartProduct.setProduct(product);
        newCartProduct.setUnitPrice(product.getUnitPrice());
        newCartProduct.setCart(cart);

        Optional<CartProduct> existingCartProduct = cartProductRepository.findByProductIdAndCartId(product.getId(), cart.getId());

        //TODO maybe something can be more clear with this logic
        if (existingCartProduct.isPresent()) {
            Integer finalQuantity = existingCartProduct.get().getQuantity() + quantity;
            if (finalQuantity > 0) {
                existingCartProduct.get().setQuantity(finalQuantity);
                return existingCartProduct.get();
            } else {
                throw new MalformedRequestPayloadException(ErrorCode.CART_PRODUCT_QUANTITY_MUST_BE_POSITIVE);
            }
        }
        return newCartProduct;
    }
}
