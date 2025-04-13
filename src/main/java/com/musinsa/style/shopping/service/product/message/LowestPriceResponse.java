package com.musinsa.style.shopping.service.product.message;

import com.musinsa.style.shopping.service.product.dto.LowestPriceItemDto;

import java.util.List;

public record LowestPriceResponse(
    List<LowestPriceItemDto> items,
    Long totalPrice
) {}