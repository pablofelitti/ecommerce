package com.ecommerce.service;

import com.ecommerce.dto.CartCreationDTO;
import com.ecommerce.dto.CartDTO;

public interface CartService {
    CartDTO create(CartCreationDTO cartCreationDTO);
}
