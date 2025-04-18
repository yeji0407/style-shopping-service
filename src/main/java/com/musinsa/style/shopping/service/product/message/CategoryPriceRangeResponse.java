package com.musinsa.style.shopping.service.product.message;

import com.musinsa.style.shopping.service.product.message.element.CategoryPriceRangeProduct;

public record CategoryPriceRangeResponse(
    String categoryName,
    CategoryPriceRangeProduct lowest,
    CategoryPriceRangeProduct highest
) {}