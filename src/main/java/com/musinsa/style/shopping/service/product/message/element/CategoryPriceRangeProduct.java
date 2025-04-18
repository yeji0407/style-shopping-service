package com.musinsa.style.shopping.service.product.message.element;

import com.musinsa.style.shopping.service.product.dto.ProductDetailInfo;

public record CategoryPriceRangeProduct(
    String brandName,
    Long price
) {
    public static CategoryPriceRangeProduct from(ProductDetailInfo productDetailInfo) {
        return new CategoryPriceRangeProduct(productDetailInfo.brandName(), productDetailInfo.price());
    }
}