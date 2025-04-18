package com.musinsa.style.shopping.service.product.message;

import com.musinsa.style.shopping.service.product.message.element.LowestPriceProduct;

import java.util.List;

public record LowestPriceResponse(List<LowestPriceProduct> products, Long totalPrice
) {}