package com.ecommerce.repository;

import com.ecommerce.entity.CartProduct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartProductRepository extends CrudRepository<CartProduct, Long> {
    Optional<CartProduct> findByProductIdAndCartId(Long productId, Long cartId);
}
