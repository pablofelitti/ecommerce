package com.ecommerce.controller;

import com.ecommerce.dto.AddCartProductDTO;
import com.ecommerce.dto.CartCreationDTO;
import com.ecommerce.dto.CartDTO;
import com.ecommerce.dto.CartProductDTO;
import com.ecommerce.service.CartProductService;
import com.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles Cart API requests
 */
@RestController
@RequestMapping("carts")
public class CartController {

    private CartService cartService;
    private CartProductService cartProductService;

    @Autowired
    CartController(final CartService cartService, final CartProductService cartProductService) {
        this.cartService = cartService;
        this.cartProductService = cartProductService;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartDTO> createCart(@RequestBody final CartCreationDTO cartCreationDTO) {
        return new ResponseEntity<>(cartService.create(cartCreationDTO), HttpStatus.OK);
    }

    @PostMapping(
            path = "{cartId}/products",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartProductDTO> addProductToCart(@PathVariable("cartId") final Long cartId,
                                                           @RequestBody final AddCartProductDTO addCartProductDTO) {
        return new ResponseEntity<>(cartProductService.addProductToCart(cartId, addCartProductDTO), HttpStatus.OK);
    }

    @DeleteMapping(
            path = "{cartId}/products/{productId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteCartProduct(@PathVariable("cartId") final Long cartId,
                                            @PathVariable("productId") final Long productId) {
        cartProductService.deleteProductFromCart(cartId, productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "{cartId}/products")
    public ResponseEntity<List<CartProductDTO>> getCartProducts(@PathVariable final Long cartId) {
        return new ResponseEntity<>(cartProductService.getCartProducts(cartId), HttpStatus.OK);
    }

    @GetMapping(path = "{cartId}")
    public ResponseEntity<CartDTO> getCart(@PathVariable final Long cartId) {
        return new ResponseEntity<>(cartService.getCart(cartId), HttpStatus.OK);
    }
}
