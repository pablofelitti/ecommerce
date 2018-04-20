package com.ecommerce.validator;

import com.ecommerce.dto.CartCreationDTO;
import com.ecommerce.exception.ErrorCode;
import com.ecommerce.validator.rule.EmailRule;
import com.ecommerce.validator.rule.EmptyStringRule;
import org.springframework.stereotype.Component;

@Component
public class CartCreationValidator {

    //TODO can we still make the validation approach better?

    public void validate(final CartCreationDTO cartCreationDTO) {
        new EmptyStringRule(ErrorCode.FULLNAME_CANNOT_BE_EMPTY).validate(cartCreationDTO.getFullName());
        new EmailRule().validate(cartCreationDTO.getEmail());
    }
}
