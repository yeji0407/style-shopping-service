package com.musinsa.style.shopping.service.common.config.cache.annotation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class EnumCacheAspect {

    private final CacheManager cacheManager;
    private final SpelExpressionParser parser = new SpelExpressionParser();

    @Around("@annotation(enumCacheable)")
    public Object handleCacheable(ProceedingJoinPoint joinPoint, EnumCacheable enumCacheable) throws Throwable {
        String cacheName = enumCacheable.value().name();
        Cache cache = cacheManager.getCache(cacheName);

        if (cache == null) {
            return joinPoint.proceed();
        }

        Object key;
        if (!enumCacheable.key().isBlank()) {
            key = evaluateKey(joinPoint, enumCacheable.key());
        } else {
            key = generateSimpleKey(joinPoint.getArgs());
        }

        Cache.ValueWrapper cached = cache.get(key);
        if (cached != null) {
            return cached.get();
        }

        Object result = joinPoint.proceed();
        if (result != null) {
            cache.put(key, result);
        }
        return result;
    }

    @AfterReturning(pointcut = "@annotation(evict)", returning = "result")
    public void handleEvict(JoinPoint joinPoint, EnumCacheEvict evict, Object result) {
        evictInternal(joinPoint, evict);
    }

    @AfterReturning(pointcut = "@annotation(evicts)", returning = "result")
    public void handleEvicts(JoinPoint joinPoint, EnumCacheEvicts evicts, Object result) {
        for (EnumCacheEvict evict : evicts.value()) {
            evictInternal(joinPoint, evict);
        }
    }

    private void evictInternal(JoinPoint joinPoint, EnumCacheEvict evict) {
        String cacheName = evict.value().name();
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) return;

        if (evict.allEntries()) {
            cache.clear();
            return;
        }

        Object key;
        if (!evict.key().isBlank()) {
            key = evaluateKey(joinPoint, evict.key());
        } else {
            key = generateSimpleKey(joinPoint.getArgs());
        }

        cache.evictIfPresent(key);
    }

    private Object evaluateKey(JoinPoint joinPoint, String keyExpression) {
        EvaluationContext context = new StandardEvaluationContext();
        String[] paramNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < paramNames.length; i++) {
            context.setVariable(paramNames[i], args[i]);
        }
        return parser.parseExpression(keyExpression).getValue(context);
    }

    private Object generateSimpleKey(Object[] args) {
        if (args == null || args.length == 0) return SimpleKey.EMPTY;
        if (args.length == 1) return args[0];
        return new SimpleKey(args);
    }
}