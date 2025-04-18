package com.musinsa.style.shopping.service.common.config.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Stream;

@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager manager = new SimpleCacheManager();

        List<CaffeineCache> caches = Stream.of(CacheSpec.values())
                .map(spec -> new CaffeineCache(
                        spec.name(),
                        Caffeine.newBuilder()
                                .expireAfterWrite(spec.getTtl(), spec.getTtlUnit())
                                .maximumSize(spec.getMaximumSize())
                                .build()
                ))
                .toList();

        manager.setCaches(caches);
        return manager;
    }
}