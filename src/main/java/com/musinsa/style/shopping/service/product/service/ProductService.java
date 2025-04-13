package com.musinsa.style.shopping.service.product.service;

import com.musinsa.style.shopping.service.common.persistence.jpa.product.repository.ProductRepository;
import com.musinsa.style.shopping.service.product.dto.LowestPriceItemDto;
import com.musinsa.style.shopping.service.product.message.LowestPriceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public LowestPriceResponse getLowestPricesByCategory() {
        List<LowestPriceItemDto> items = productRepository.findLowestPriceByCategory();
        long total = items.stream().mapToLong(LowestPriceItemDto::price).sum();
        return new LowestPriceResponse(items, total);
    }
}