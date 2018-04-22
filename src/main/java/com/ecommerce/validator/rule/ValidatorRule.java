package com.ecommerce.validator.rule;

/**
 * Validator that performs validation and does not return anything
 *
 * @param <T> Object to validate
 */
public interface ValidatorRule<T> {
    void validate(T dataToValidate);
}
