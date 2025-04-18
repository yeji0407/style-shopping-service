package com.musinsa.style.shopping.service.common.config.cache.annotation;

import com.musinsa.style.shopping.service.common.config.cache.CacheSpec;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnumCacheable {
    CacheSpec value();
    String key() default "";
}