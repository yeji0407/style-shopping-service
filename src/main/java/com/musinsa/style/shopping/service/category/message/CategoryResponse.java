package com.musinsa.style.shopping.service.category.message;

import com.musinsa.style.shopping.service.common.persistence.jpa.category.entity.Category;

public record CategoryResponse(Long id, String name) {
    public static CategoryResponse from(Category category) {
        return new CategoryResponse(category.getCategoryId(), category.getCategoryName());
    }
}