package com.ecommerce.validator.rule;

/**
 * Validator that performs validation and returns an object
 *
 * @param <T> Object to validate
 * @param <K> Object to return
 */
public interface ValidatorResponseRule<T, K> {
    K validate(final T dataToValidate);
}
