package com.ecommerce.converter;

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.entity.Product;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductDTOConverter implements Converter<Product, ProductDTO> {

    @Override
    public ProductDTO convert(final Product product) {
        return new ProductDTO(product.getId(), product.getDescription(), product.getUnitPrice(), product.getStock());
    }
}
