package com.musinsa.style.shopping.service.common.exception;

import com.musinsa.style.shopping.service.common.code.ErrorCode;
import lombok.Getter;

public class BusinessException extends RuntimeException {

    @Getter
    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return errorCode.getMessage();
    }
}