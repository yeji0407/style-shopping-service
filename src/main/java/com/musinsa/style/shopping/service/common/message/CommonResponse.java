package com.musinsa.style.shopping.service.common.message;

import com.musinsa.style.shopping.service.common.code.ErrorCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommonResponse<T> {
    private boolean success;
    private T data;
    private String code;
    private String message;
    private List<FieldErrorDetail> errors;

    public static <T> CommonResponse<T> success(T data) {
        CommonResponse<T> res = new CommonResponse<>();
        res.success = true;
        res.data = data;
        return res;
    }

    public static <T> CommonResponse<T> fail(ErrorCode code) {
        CommonResponse<T> res = new CommonResponse<>();
        res.success = false;
        res.code = code.getCode();
        res.message = code.getMessage();
        return res;
    }

    public static <T> CommonResponse<T> fail(ErrorCode code, List<FieldErrorDetail> errors) {
        CommonResponse<T> res = fail(code);
        res.errors = errors;
        return res;
    }
}