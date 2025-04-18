package com.musinsa.style.shopping.service.product.message;

import com.musinsa.style.shopping.service.product.message.element.BrandLowestPriceCategory;

import java.util.List;

public record BrandLowestPriceResponse(
    String brandName,
    List<BrandLowestPriceCategory> categories,
    Long totalPrice
) {}