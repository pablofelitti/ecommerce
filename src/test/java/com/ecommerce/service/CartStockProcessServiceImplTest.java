package com.ecommerce.service;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartProduct;
import com.ecommerce.entity.CartStatus;
import com.ecommerce.entity.Product;
import com.ecommerce.exception.ErrorCode;
import com.ecommerce.exception.MalformedRequestPayloadException;
import com.ecommerce.exception.ResourceDoesNotExistException;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CartStockProcessServiceImplTest {

    @InjectMocks
    private CartStockProcessServiceImpl service;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CartRepository cartRepository;

    @Test
    public void whenProcessCartIsNullThenThrowException() {
        try {
            service.processCart(null);
            fail("Should have thrown exception");
        } catch (MalformedRequestPayloadException e) {
            assertEquals(ErrorCode.CART_CANNOT_BE_EMPTY, e.getErrorCode());
        }
    }

    @Test
    public void whenCartIsInNewStatusThenThrowException() {
        testCartStatusFailedValidation(CartStatus.NEW);
    }

    @Test
    public void whenCartIsInProcessedStatusThenThrowException() {
        testCartStatusFailedValidation(CartStatus.PROCESSED);
    }

    @Test
    public void whenCartIsInFailedStatusThenThrowException() {
        testCartStatusFailedValidation(CartStatus.FAILED);
    }

    @Test
    public void whenCartIsNotFoundThenThrowException() {
        when(cartRepository.findById(55L)).thenReturn(Optional.empty());

        try {
            service.processCart(55L);
            fail("Should not have accepted to process cart");
        } catch (ResourceDoesNotExistException e) {
            assertEquals(ErrorCode.CART_DOES_NOT_EXIST, e.getErrorCode());
        }
    }

    @Test
    public void whenCartHasOneProductAndRemainingStockIsPositiveThenCartIsProcessed() {
        Product product = createProduct(3);
        Cart cart = createCart(CartStatus.READY);
        cart.addCartProduct(createCartProduct(1, product));
        when(cartRepository.findById(55L)).thenReturn(Optional.of(cart));

        Cart cartResult = service.processCart(55L);

        verify(cartRepository).findById(55L);
        ArgumentCaptor<Cart> cartCaptor = ArgumentCaptor.forClass(Cart.class);
        verify(cartRepository).save(cartCaptor.capture());
        assertEquals(CartStatus.PROCESSED, cartCaptor.getValue().getStatus());
        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(productCaptor.capture());
        assertEquals(new Integer(2), productCaptor.getValue().getStock());
        assertEquals(cart, cartResult);
    }

    @Test
    public void whenCartHasTwoProductsAndRemainingStockIsPositiveThenCartIsProcessed() {
        Cart cart = createCart(CartStatus.READY);
        Product product1 = createProduct(3);
        cart.addCartProduct(createCartProduct(1, product1));
        Product product2 = createProduct(5);
        cart.addCartProduct(createCartProduct(2, product2));
        when(cartRepository.findById(55L)).thenReturn(Optional.of(cart));

        service.processCart(55L);

        ArgumentCaptor<Cart> cartCaptor = ArgumentCaptor.forClass(Cart.class);
        verify(cartRepository).save(cartCaptor.capture());
        assertEquals(CartStatus.PROCESSED, cartCaptor.getValue().getStatus());
        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository, times(2)).save(productCaptor.capture());
        assertEquals(new Integer(2), productCaptor.getAllValues().get(0).getStock());
        assertEquals(new Integer(3), productCaptor.getAllValues().get(1).getStock());
    }

    @Test
    public void whenCartHasOneProductAndRemainingStockIsNegativeThenSetStatusFailed() {
        Cart cart = createCart(CartStatus.READY);
        Product product = createProduct(3);
        cart.addCartProduct(createCartProduct(4, product));
        when(cartRepository.findById(55L)).thenReturn(Optional.of(cart));

        service.processCart(55L);

        ArgumentCaptor<Cart> argumentCaptor = ArgumentCaptor.forClass(Cart.class);
        verify(cartRepository).save(argumentCaptor.capture());
        assertEquals(CartStatus.FAILED, argumentCaptor.getValue().getStatus());
        verify(productRepository, never()).save(any(Product.class));
    }

    private void testCartStatusFailedValidation(CartStatus cartStatus) {
        Cart cart = createCart(cartStatus);
        when(cartRepository.findById(55L)).thenReturn(Optional.of(cart));

        try {
            service.processCart(55L);
            fail("Should not have accepted to process cart");
        } catch (MalformedRequestPayloadException e) {
            assertEquals(ErrorCode.CART_STATUS_NOT_READY, e.getErrorCode());
        }
    }

    private Product createProduct(Integer stock) {
        Product product = new Product();
        product.setStock(stock);
        return product;
    }

    private CartProduct createCartProduct(int quantity, Product product) {
        CartProduct cartProduct = new CartProduct();
        cartProduct.setQuantity(quantity);
        cartProduct.setProduct(product);
        return cartProduct;
    }

    private Cart createCart(CartStatus status) {
        Cart cart = new Cart();
        cart.setStatus(status);
        return cart;
    }
}