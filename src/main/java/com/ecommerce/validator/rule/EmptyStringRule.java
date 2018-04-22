package com.ecommerce.validator.rule;

import com.ecommerce.exception.ErrorCode;
import com.ecommerce.exception.MalformedRequestPayloadException;
import org.springframework.util.StringUtils;

/**
 * Rule that validates if the given string is empty
 */
public class EmptyStringRule implements ValidatorRule<String> {

    private final ErrorCode errorCode;

    public EmptyStringRule(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public void validate(final String dataToValidate) {
        if (StringUtils.isEmpty(dataToValidate)) {
            throw new MalformedRequestPayloadException(errorCode);
        }
    }
}
