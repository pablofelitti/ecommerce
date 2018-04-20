package com.ecommerce.validator.rule;

public interface ValidatorResponseRule<T, K> {
    K validate(T dataToValidate);
}
