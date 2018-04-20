package com.ecommerce.validator.rule;

import com.ecommerce.exception.ErrorCode;
import com.ecommerce.exception.MalformedRequestPayloadException;
import org.springframework.util.StringUtils;

public class EmptyStringRule implements ValidatorRule<String> {

    private ErrorCode errorCode;

    public EmptyStringRule(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public void validate(String dataToValidate) {
        if (StringUtils.isEmpty(dataToValidate)) {
            throw new MalformedRequestPayloadException(errorCode);
        }
    }
}
