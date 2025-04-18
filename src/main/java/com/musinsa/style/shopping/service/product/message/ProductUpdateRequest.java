package com.musinsa.style.shopping.service.product.message;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductUpdateRequest(
    @NotNull @Positive Long price
) {}