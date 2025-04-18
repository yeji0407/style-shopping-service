package com.musinsa.style.shopping.service.common.config.cache.annotation;

import com.musinsa.style.shopping.service.common.config.cache.CacheSpec;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(EnumCacheEvicts.class)
public @interface EnumCacheEvict {
    CacheSpec value();
    boolean allEntries() default false;
    String key() default "";
}