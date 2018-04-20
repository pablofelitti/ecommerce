package com.ecommerce.validator.rule;

import com.ecommerce.exception.ErrorCode;
import com.ecommerce.exception.MalformedRequestPayloadException;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailRule implements ValidatorRule<String> {

    private static final Pattern emailPattern = Pattern.compile("^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$");

    @Override
    public void validate(String dataToValidate) {

        if (StringUtils.isEmpty(dataToValidate)) {
            throw new MalformedRequestPayloadException(ErrorCode.EMAIL_CANNOT_BE_EMPTY);
        }

        Matcher matcher = emailPattern.matcher(dataToValidate);
        if (!matcher.matches()) {
            throw new MalformedRequestPayloadException(ErrorCode.EMAIL_FORMAT_INCORRECT);
        }
    }
}
