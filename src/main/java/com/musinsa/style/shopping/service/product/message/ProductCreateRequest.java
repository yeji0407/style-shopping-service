package com.musinsa.style.shopping.service.product.message;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductCreateRequest(
    @NotNull Long brandId,
    @NotNull Long categoryId,
    @NotNull @Positive Long price
) {}