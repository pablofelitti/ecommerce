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

    private final CartProductRepository cartProductRepository;
    private final CartRepository cartRepository;
    private final CartProductDTOConverter cartProductDTOConverter;
    private final CreateCartProductValidator createCartProductValidator;
    private final DeleteProductFromCartValidator deleteProductFromCartValidator;
    private final GetCartValidator getCartValidator;

    public CartProductServiceImpl(final CartProductRepository cartProductRepository,
                                  final CartRepository cartRepository,
                                  final CartProductDTOConverter cartProductDTOConverter,
                                  final CreateCartProductValidator createCartProductValidator,
                                  final DeleteProductFromCartValidator deleteProductFromCartValidator,
                                  final GetCartValidator getCartValidator) {
        this.cartProductRepository = cartProductRepository;
        this.cartRepository = cartRepository;
        this.cartProductDTOConverter = cartProductDTOConverter;
        this.createCartProductValidator = createCartProductValidator;
        this.deleteProductFromCartValidator = deleteProductFromCartValidator;
        this.getCartValidator = getCartValidator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CartProductDTO addProductToCart(final Long cartId, final AddCartProductDTO addCartProductDTO) {
        final CreateCartProductValidationResult validationResult = createCartProductValidator.validate(cartId, addCartProductDTO);

        final Product product = validationResult.getProduct();
        final Cart cart = validationResult.getCart();

        final Predicate<CartProduct> productExistsInCart = cartProduct ->
                cartProduct.getProduct().getId().equals(product.getId());

        final Optional<CartProduct> cartProductExisting = cart.getCartProducts().stream()
                .filter(productExistsInCart)
                .findFirst();

        if (cartProductExisting.isPresent()) {

            LOGGER.debug("Product {} is present in the cart", addCartProductDTO.getProductId());

            final Integer finalQuantity = cartProductExisting.get().getQuantity() + addCartProductDTO.getQuantity();

            LOGGER.debug("New quantity will be {}", finalQuantity);

            if (finalQuantity > 0) {
                updateCartProduct(cartProductExisting.get(), finalQuantity);
                updateCartTotal(cart);
                return cartProductDTOConverter.convert(cartProductExisting.get());
            } else {
                throw new MalformedRequestPayloadException(ErrorCode.CART_PRODUCT_QUANTITY_MUST_BE_POSITIVE);
            }
        } else {

            LOGGER.debug("Product {} is not present in the cart", addCartProductDTO.getProductId());

            final CartProduct newCartProduct = createCartProduct(cart, product);

            updateCartProduct(newCartProduct, addCartProductDTO.getQuantity());
            cart.addCartProduct(newCartProduct);
            updateCartTotal(cart);

            return cartProductDTOConverter.convert(newCartProduct);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteProductFromCart(final Long cartId, final Long productId) {
        DeleteCartProductValidationResult validationResult = deleteProductFromCartValidator.validate(cartId, productId);
        validationResult.getCart().removeCartProduct(validationResult.getCartProduct());
        updateCartTotal(validationResult.getCart());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CartProductDTO> getCartProducts(final Long cartId) {
        CartValidationResult cartProducts = getCartValidator.validate(cartId);
        return cartProducts.getCart().getCartProducts().stream().
                map(cartProductDTOConverter::convert).
                collect(Collectors.toList());
    }

    private void updateCartProduct(final CartProduct cartProduct, final Integer finalQuantity) {
        cartProduct.setQuantity(finalQuantity);
        cartProductRepository.save(cartProduct);
    }

    private void updateCartTotal(final Cart cart) {
        final BigDecimal total = calculateTotalCart(cart);
        cart.setTotal(total);
        cartRepository.save(cart);
    }

    private BigDecimal calculateTotalCart(final Cart cart) {
        return cart.getCartProducts().stream().
                map(CartProduct::getTotal).
                reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    private CartProduct createCartProduct(final Cart cart, final Product product) {
        final CartProduct newCartProduct = new CartProduct();
        newCartProduct.setProduct(product);
        newCartProduct.setUnitPrice(product.getUnitPrice());
        newCartProduct.setCart(cart);

        return newCartProduct;
    }
}
