package com.musinsa.style.shopping.service.common.config.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Getter
public enum CacheSpec {
    BRAND(10, TimeUnit.SECONDS, 500),
    BRANDS(10, TimeUnit.SECONDS, 1),
    PRODUCTS(5, TimeUnit.SECONDS, 1),
    CATEGORIES(10, TimeUnit.MINUTES, 1),
    CATEGORY_NAME_MAP(10, TimeUnit.MINUTES, 1);

    private final long ttl;
    private final TimeUnit ttlUnit;
    private final long maximumSize;
}