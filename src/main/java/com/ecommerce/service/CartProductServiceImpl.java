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
import com.ecommerce.repository.CartRepository;
import com.ecommerce.validator.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class CartProductServiceImpl implements CartProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartProductServiceImpl.class);

    private CartProductRepository cartProductRepository;
    private CartRepository cartRepository;
    private CartProductDTOConverter cartProductDTOConverter;
    private CartProductCreationValidator cartProductCreationValidator;
    private DeleteProductFromCartValidator deleteProductFromCartValidator;
    private GetCartProductsValidator getCartProductsValidator;

    public CartProductServiceImpl(final CartProductRepository cartProductRepository,
                                  final CartRepository cartRepository,
                                  final CartProductDTOConverter cartProductDTOConverter,
                                  final CartProductCreationValidator cartProductCreationValidator,
                                  final DeleteProductFromCartValidator deleteProductFromCartValidator,
                                  final GetCartProductsValidator getCartProductsValidator) {
        this.cartProductRepository = cartProductRepository;
        this.cartRepository = cartRepository;
        this.cartProductDTOConverter = cartProductDTOConverter;
        this.cartProductCreationValidator = cartProductCreationValidator;
        this.deleteProductFromCartValidator = deleteProductFromCartValidator;
        this.getCartProductsValidator = getCartProductsValidator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CartProductDTO addProductToCart(final Long cartId, final AddCartProductDTO addCartProductDTO) {
        CartProductValidationResult validationResult = cartProductCreationValidator.validate(cartId, addCartProductDTO);

        Predicate<CartProduct> productExists = cp -> cp.getProduct().getId().equals(validationResult.getProduct().getId());

        Optional<CartProduct> cartProductExisting = validationResult.getCart().getCartProducts().stream().
                filter(productExists).
                findFirst();

        if (cartProductExisting.isPresent()) {

            LOGGER.debug("Product {} is present in the cart", addCartProductDTO.getProductId());

            Integer finalQuantity = cartProductExisting.get().getQuantity() + addCartProductDTO.getQuantity();

            LOGGER.debug("New quantity will be {}", finalQuantity);

            if (finalQuantity > 0) {

                updateCartProduct(cartProductExisting.get(), finalQuantity);
                updateCartTotal(validationResult.getCart(), null);

                return cartProductDTOConverter.convert(cartProductExisting.get());
            } else {
                throw new MalformedRequestPayloadException(ErrorCode.CART_PRODUCT_QUANTITY_MUST_BE_POSITIVE);
            }
        } else {

            LOGGER.debug("Product {} is not present in the cart", addCartProductDTO.getProductId());

            CartProduct newCartProduct = createCartProduct(validationResult.getCart(), validationResult.getProduct());

            updateCartProduct(newCartProduct, addCartProductDTO.getQuantity());
            updateCartTotal(validationResult.getCart(), newCartProduct.getTotal());

            return cartProductDTOConverter.convert(newCartProduct);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteProductFromCart(final Long cartId, final Long productId) {
        DeleteCartProductValidationResult validationResult = deleteProductFromCartValidator.validate(cartId, productId);
        validationResult.getCart().getCartProducts().remove(validationResult.getCartProduct());
        updateCartTotal(validationResult.getCart(), null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CartProductDTO> getCartProducts(final Long cartId) {
        GetCartProductsValidationResult cartProducts = getCartProductsValidator.validate(cartId);
        return cartProducts.getCart().getCartProducts().stream().
                map(cartProduct -> cartProductDTOConverter.convert(cartProduct)).
                collect(Collectors.toList());
    }

    private void updateCartProduct(final CartProduct cartProduct, final Integer finalQuantity) {
        cartProduct.setQuantity(finalQuantity);
        cartProductRepository.save(cartProduct);
    }

    private void updateCartTotal(final Cart cart, final BigDecimal firstTime) {
        BigDecimal total = calculateTotalCart(cart);

        //TODO maybe this can be removed from the method parameter
        if (firstTime != null) {
            cart.setTotal(total.add(firstTime));
        } else {
            cart.setTotal(total);
        }
        cartRepository.save(cart);
    }

    private BigDecimal calculateTotalCart(final Cart cart) {
        return cart.getCartProducts().stream().
                    map(CartProduct::getTotal).
                    reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    private CartProduct createCartProduct(final Cart cart, final Product product) {
        CartProduct newCartProduct = new CartProduct();
        newCartProduct.setProduct(product);
        newCartProduct.setUnitPrice(product.getUnitPrice());
        newCartProduct.setCart(cart);

        return newCartProduct;
    }
}
