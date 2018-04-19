package com.ecommerce.controller;

import com.ecommerce.dto.CartCreationDTO;
import com.ecommerce.dto.CartDTO;
import com.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles Cart API requests
 */
@RestController
@RequestMapping("carts")
public class CartController {

    private CartService cartService;

    @Autowired
    CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartDTO> createCart(@RequestBody final CartCreationDTO cartCreationDTO) {
        return new ResponseEntity<>(cartService.create(cartCreationDTO), HttpStatus.OK);
    }
}
