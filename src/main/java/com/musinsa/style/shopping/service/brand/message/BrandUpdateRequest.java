package com.musinsa.style.shopping.service.brand.message;

import jakarta.validation.constraints.NotBlank;

public record BrandUpdateRequest(
    @NotBlank(message = "브랜드명은 필수입니다.")
    String name
) {}