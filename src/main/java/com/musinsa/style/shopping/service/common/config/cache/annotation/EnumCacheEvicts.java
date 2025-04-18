package com.musinsa.style.shopping.service.common.config.cache.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnumCacheEvicts {
    EnumCacheEvict[] value();
}