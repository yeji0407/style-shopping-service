package com.musinsa.style.shopping.service.product.message;

import com.musinsa.style.shopping.service.common.persistence.jpa.product.entity.Product;

public record ProductResponse(Long id, String brandName, String categoryName, Long price) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(
            product.getProductId(),
            product.getBrand().getBrandName(),
            product.getCategory().getCategoryName(),
            product.getPrice()
        );
    }
}