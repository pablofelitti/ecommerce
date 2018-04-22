package com.ecommerce.validator.rule;

import com.ecommerce.exception.ErrorCode;
import com.ecommerce.exception.MalformedRequestPayloadException;
import org.springframework.util.StringUtils;

/**
 * Rule that validates if the given object is null
 */
public class NullRule implements ValidatorRule<Object> {

    private final ErrorCode errorCode;

    public NullRule(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public void validate(final Object dataToValidate) {
        if (StringUtils.isEmpty(dataToValidate)) {
            throw new MalformedRequestPayloadException(errorCode);
        }
    }
}
