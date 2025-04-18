package com.musinsa.style.shopping.service.brand.message;

import com.musinsa.style.shopping.service.common.persistence.jpa.brand.entity.Brand;

public record BrandResponse(Long id, String name) {
    public static BrandResponse from(Brand brand) {
        return new BrandResponse(brand.getBrandId(), brand.getBrandName());
    }
}