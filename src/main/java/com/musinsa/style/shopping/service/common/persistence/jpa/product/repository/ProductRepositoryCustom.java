package com.musinsa.style.shopping.service.common.persistence.jpa.product.repository;

import com.musinsa.style.shopping.service.product.dto.LowestPriceItemDto;

import java.util.List;

public interface ProductRepositoryCustom {
    List<LowestPriceItemDto> findLowestPriceByCategory();
}