package com.musinsa.style.shopping.service.common.exception.handler;

import com.musinsa.style.shopping.service.common.code.ErrorCode;
import com.musinsa.style.shopping.service.common.exception.BusinessException;
import com.musinsa.style.shopping.service.common.message.CommonResponse;
import com.musinsa.style.shopping.service.common.message.FieldErrorDetail;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<CommonResponse<Void>> handleBusinessException(BusinessException ex) {
        ErrorCode errorCode = ex.getErrorCode();

        return ResponseEntity
            .status(errorCode.getHttpStatus())
            .body(CommonResponse.fail(errorCode));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<Void>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<FieldErrorDetail> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new FieldErrorDetail(
                        error.getField(),
                        error.getRejectedValue(),
                        error.getDefaultMessage()
                ))
                .toList();

        return ResponseEntity
                .status(ErrorCode.INVALID_INPUT_VALUE.getHttpStatus())
                .body(CommonResponse.fail(ErrorCode.INVALID_INPUT_VALUE, fieldErrors));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<CommonResponse<Void>> handleBindException(BindException ex) {
        ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(CommonResponse.fail(errorCode));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CommonResponse<Void>> handleConstraintViolation(ConstraintViolationException ex) {
        ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(CommonResponse.fail(errorCode));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<Void>> handleException(Exception ex) {
        ex.printStackTrace();

        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(CommonResponse.fail(ErrorCode.INTERNAL_SERVER_ERROR));
    }
}