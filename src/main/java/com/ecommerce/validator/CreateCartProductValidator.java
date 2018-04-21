package com.ecommerce.validator;

import com.ecommerce.dto.AddCartProductDTO;
import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartStatus;
import com.ecommerce.entity.Product;
import com.ecommerce.exception.ErrorCode;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.validator.rule.CartExistsRule;
import com.ecommerce.validator.rule.CartStatusRule;
import com.ecommerce.validator.rule.NullRule;
import com.ecommerce.validator.rule.ProductExistsRule;
import org.springframework.stereotype.Component;

@Component
public class CreateCartProductValidator {

    private CartRepository cartRepository;
    private ProductRepository productRepository;

    CreateCartProductValidator(final CartRepository cartRepository, final ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    //TODO can we still make the validation approach better?

    public CreateCartProductValidationResult validate(Long cartId, AddCartProductDTO addCartProductDTO) {

        new NullRule(ErrorCode.PRODUCT_QUANTITY_CANNOT_BE_EMPTY).validate(addCartProductDTO.getQuantity());

        Product product = new ProductExistsRule(productRepository).validate(addCartProductDTO.getProductId());
        Cart cart = new CartExistsRule(cartRepository).validate(cartId);
        new CartStatusRule(CartStatus.NEW, ErrorCode.CART_STATUS_NOT_NEW).validate(cart);

        return new CreateCartProductValidationResult(cart, product);
    }
}
