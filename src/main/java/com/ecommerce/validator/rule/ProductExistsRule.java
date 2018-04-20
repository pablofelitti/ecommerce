package com.ecommerce.validator.rule;

import com.ecommerce.entity.Product;
import com.ecommerce.exception.ErrorCode;
import com.ecommerce.exception.MalformedRequestPayloadException;
import com.ecommerce.exception.ResourceDoesNotExistException;
import com.ecommerce.repository.ProductRepository;

import java.util.Optional;

public class ProductExistsRule implements ValidatorResponseRule<Long, Product> {

    private final ProductRepository productRepository;

    public ProductExistsRule(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product validate(Long id) {

        if (id == null) {
            throw new MalformedRequestPayloadException(ErrorCode.PRODUCT_CANNOT_BE_EMPTY);
        }

        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            throw new ResourceDoesNotExistException(ErrorCode.PRODUCT_DOES_NOT_EXIST);
        }
        return product.get();
    }
}
