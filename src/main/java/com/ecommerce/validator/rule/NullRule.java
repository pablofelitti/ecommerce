package com.ecommerce.validator.rule;

import com.ecommerce.exception.ErrorCode;
import com.ecommerce.exception.MalformedRequestPayloadException;
import org.springframework.util.StringUtils;

public class NullRule implements ValidatorRule<Object> {

    private ErrorCode errorCode;

    public NullRule(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public void validate(Object dataToValidate) {
        if (StringUtils.isEmpty(dataToValidate)) {
            throw new MalformedRequestPayloadException(errorCode);
        }
    }
}
