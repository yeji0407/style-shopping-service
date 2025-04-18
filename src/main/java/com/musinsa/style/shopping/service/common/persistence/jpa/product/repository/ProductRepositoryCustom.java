package com.musinsa.style.shopping.service.common.persistence.jpa.product.repository;

import com.musinsa.style.shopping.service.product.dto.ProductDetailInfo;

import java.util.List;

public interface ProductRepositoryCustom {
    /**
     * 카테고리별 최저가 상품을 조회합니다.
     *
     * @return 카테고리별 최저가 상품 리스트
     */
    List<ProductDetailInfo> findLowestPriceByCategory();

    /**
     * 총액 최저가의 단일 브랜드 카테고리별 상품을 조회합니다.
     *
     * @return 총액 최저가의 단일 브랜드 카테고리별 상품 리스트
     */
    List<ProductDetailInfo> findBrandWithLowestTotalPrice();

    /**
     * 특정 카테고리의 상품을 가격으로 정렬하여 조회합니다.
     *
     * @param categoryName 카테고리 이름
     * @return 정렬된 상품 리스트
     */
    List<ProductDetailInfo> findByCategoryOrderByPrice(String categoryName);
}