package com.musinsa.style.shopping.service.product.message.element;

import com.musinsa.style.shopping.service.product.dto.ProductDetailInfo;

public record LowestPriceProduct(
        String categoryName,
        String brandName,
        Long price
) {
    public static LowestPriceProduct from(ProductDetailInfo product) {
        return new LowestPriceProduct(
                product.categoryName(),
                product.brandName(),
                product.price()
        );
    }
}