package com.ecommerce.exception;

import com.ecommerce.dto.ErrorDetails;
import com.ecommerce.dto.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * Responsible of handling exceptions for the API
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Exception handler when a requested resource does not exist
     *
     * @param ex Exception that occurred
     * @param request Request
     * @return Error details
     */
    @ExceptionHandler(ResourceDoesNotExistException.class)
    public final ResponseEntity<ErrorDetails> handleResourceDoesNotExistException(final ResourceDoesNotExistException ex, final WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new ErrorInfo(ex.getErrorCode().getCode(), ex.getErrorCode().getMessage()));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler when there is invalid does not meet a business requirement validation (eg: empty value)
     *
     * @param ex Exception that occurred
     * @param request Request
     * @return Error details
     */
    @ExceptionHandler(MalformedRequestPayloadException.class)
    public final ResponseEntity<ErrorDetails> handleInvalidRequestException(final MalformedRequestPayloadException ex, final WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new ErrorInfo(ex.getErrorCode().getCode(), ex.getErrorCode().getMessage()));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler when there is a POST or PUT with malformed json or wrong data type (eg: missing semicolon
     * or string instead of number)
     *
     * @param ex Exception that occurred
     * @param request Request
     * @return Error details
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<ErrorDetails> handleIncorrectJsonException(final HttpMessageNotReadableException ex, final WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new ErrorInfo(ErrorCode.MALFORMED_JSON.getCode(), ErrorCode.MALFORMED_JSON.getMessage()));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Default exception handler (eg: NPE in any part of the code)
     *
     * @param ex Exception that occurred
     * @param request Request
     * @return Error details
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleDefaultException(final Exception ex, final WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new ErrorInfo(ErrorCode.DEFAULT.getCode(), ErrorCode.DEFAULT.getMessage()));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
