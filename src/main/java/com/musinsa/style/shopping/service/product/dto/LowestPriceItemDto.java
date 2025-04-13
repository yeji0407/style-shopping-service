package com.musinsa.style.shopping.service.product.dto;

public record LowestPriceItemDto(
    Long categoryId,
    String categoryName,
    Long brandId,
    String brandName,
    Long price
) {}