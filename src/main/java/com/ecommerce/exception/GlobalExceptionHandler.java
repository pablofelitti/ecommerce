package com.ecommerce.exception;

import com.ecommerce.dto.ErrorDetails;
import com.ecommerce.dto.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * Responsible of handling exceptions for the API
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MalformedRequestPayloadException.class)
    public final ResponseEntity<ErrorDetails> handleInvalidRequestException(final MalformedRequestPayloadException ex, final WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new ErrorInfo(ex.getErrorCode().getCode(), ex.getErrorCode().getMessage()));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleDefaultException(final Exception ex, final WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new ErrorInfo(ErrorCode.DEFAULT.getCode(), ErrorCode.DEFAULT.getMessage()));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
