package com.musinsa.style.shopping.service.category.service;

import com.musinsa.style.shopping.service.category.message.CategoryResponse;
import com.musinsa.style.shopping.service.common.config.cache.CacheSpec;
import com.musinsa.style.shopping.service.common.config.cache.annotation.EnumCacheable;
import com.musinsa.style.shopping.service.common.persistence.jpa.category.entity.Category;
import com.musinsa.style.shopping.service.common.persistence.jpa.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * 삭제되지 않은 모든 카테고리를 조회
     *
     * @return 카테고리 리스트
     */
    @EnumCacheable(CacheSpec.CATEGORIES)
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAllByIsDeletedFalse().stream()
                .map(CategoryResponse::from)
                .toList();
    }

    /**
     * 카테고리 이름 맵 조회
     *
     * @return 카테고리 이름 맵
     */
    @EnumCacheable(CacheSpec.CATEGORY_NAME_MAP)
    public Map<Long, String> getCategoryNameMap() {
        return categoryRepository.findAllByIsDeletedFalse().stream()
                .collect(Collectors.toMap(Category::getCategoryId, Category::getCategoryName));
    }
}
