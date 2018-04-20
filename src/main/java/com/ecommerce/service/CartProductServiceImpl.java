package com.ecommerce.service;

import com.ecommerce.converter.CartProductDTOConverter;
import com.ecommerce.dto.AddCartProductDTO;
import com.ecommerce.dto.CartProductDTO;
import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartProduct;
import com.ecommerce.entity.Product;
import com.ecommerce.exception.ErrorCode;
import com.ecommerce.exception.MalformedRequestPayloadException;
import com.ecommerce.exception.ResourceDoesNotExistException;
import com.ecommerce.repository.CartProductRepository;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartProductServiceImpl implements CartProductService {

    private ProductRepository productRepository;
    private CartProductRepository cartProductRepository;
    private CartProductDTOConverter cartProductDTOConverter;
    private CartRepository cartRepository;

    public CartProductServiceImpl(final ProductRepository productRepository,
                                  final CartProductRepository cartProductRepository,
                                  final CartProductDTOConverter cartProductDTOConverter,
                                  final CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.cartProductRepository = cartProductRepository;
        this.cartProductDTOConverter = cartProductDTOConverter;
        this.cartRepository = cartRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CartProductDTO addProductToCart(Long cartId, final AddCartProductDTO addCartProductDTO) {
        validateParameters(cartId, addCartProductDTO);

        CartProduct newCartProduct = createCartProduct(cartId, addCartProductDTO);

        CartProduct savedCartProduct = cartProductRepository.save(newCartProduct);

        return cartProductDTOConverter.convert(savedCartProduct);
    }

    private CartProduct createCartProduct(Long cartId, AddCartProductDTO addCartProductDTO) {
        CartProduct newCartProduct = new CartProduct();
        newCartProduct.setQuantity(addCartProductDTO.getQuantity());

        //TODO maybe the same validation can return the product so it is more optimized
        Optional<Product> product = productRepository.findById(addCartProductDTO.getProductId());
        newCartProduct.setProduct(product.get());
        newCartProduct.setUnitPrice(product.get().getUnitPrice());

        Optional<Cart> cart = cartRepository.findById(cartId);
        newCartProduct.setCart(cart.get());

        Optional<CartProduct> cartProduct = cartProductRepository.findByProductIdAndCartId(addCartProductDTO.getProductId(), cartId);
        if (cartProduct.isPresent()) {
            //TODO if it exists in the cart then we keep the same price the cart had the first time the product was added
            Integer finalQuantity = cartProduct.get().getQuantity() + addCartProductDTO.getQuantity();
            if (finalQuantity > 0) {
                cartProduct.get().setQuantity(finalQuantity);
                return cartProduct.get();
            } else {
                //TODO if it is 0 we could just remove it maybe
                throw new MalformedRequestPayloadException(ErrorCode.CART_PRODUCT_QUANTITY_MUST_BE_POSITIVE);
            }
        }
        return newCartProduct;
    }

    private void validateParameters(Long cartId, final AddCartProductDTO addCartProductDTO) {

        if (!cartExists(cartId)) {
            throw new ResourceDoesNotExistException(ErrorCode.CART_DOES_NOT_EXIST);
        }

        if (addCartProductDTO.getProductId() == null) {
            throw new MalformedRequestPayloadException(ErrorCode.PRODUCT_CANNOT_BE_EMPTY);
        }

        if (!productExists(addCartProductDTO)) {
            throw new ResourceDoesNotExistException(ErrorCode.PRODUCT_DOES_NOT_EXIST);
        }

        if (addCartProductDTO.getQuantity() == null) {
            throw new MalformedRequestPayloadException(ErrorCode.PRODUCT_QUANTITY_CANNOT_BE_EMPTY);
        }
    }

    private boolean productExists(AddCartProductDTO addCartProductDTO) {
        return productRepository.existsById(addCartProductDTO.getProductId());
    }

    private boolean cartExists(Long cartId) {
        return cartRepository.existsById(cartId);
    }
}
