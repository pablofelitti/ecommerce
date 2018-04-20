package com.ecommerce.validator.rule;

public interface ValidatorRule<T> {
    void validate(T dataToValidate);
}
