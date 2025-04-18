package com.musinsa.style.shopping.service.product.dto;

public record ProductDetailInfo(
        Long productId,
        Long categoryId,
        String categoryName,
        Long brandId,
        String brandName,
        Long price
) {
}