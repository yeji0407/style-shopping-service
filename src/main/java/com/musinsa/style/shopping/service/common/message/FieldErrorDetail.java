package com.musinsa.style.shopping.service.common.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FieldErrorDetail {
    private String field;
    private Object value;
    private String reason;
}