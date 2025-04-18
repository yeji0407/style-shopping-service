package com.musinsa.style.shopping.service.product.message;

import jakarta.validation.constraints.NotBlank;

public record CategoryPriceRequest(
    @NotBlank(message = "카테고리명을 입력해주세요")
    String categoryName
) {}