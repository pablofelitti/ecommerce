package com.ecommerce.converter;

import com.ecommerce.dto.CartProductDTO;
import com.ecommerce.dto.ProductDTO;
import com.ecommerce.entity.CartProduct;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CartProductDTOConverter implements Converter<CartProduct, CartProductDTO> {

    private final ProductDTOConverter productDTOConverter;

    CartProductDTOConverter(final ProductDTOConverter productDTOConverter) {
        this.productDTOConverter = productDTOConverter;
    }

    @Override
    public CartProductDTO convert(final CartProduct cartProduct) {
        final ProductDTO productDTO = productDTOConverter.convert(cartProduct.getProduct());

        return new CartProductDTO(cartProduct.getId(),
                productDTO, cartProduct.getQuantity(), cartProduct.getUnitPrice());
    }
}
