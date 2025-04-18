package com.musinsa.style.shopping.service.common.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class GroupingUtils {

    private GroupingUtils() {
        // private constructor to prevent instantiation
    }

    /**
     * 리스트에서 특정 키 기준으로 그룹화한 뒤, 각 그룹의 첫 번째 항목만 반환
     *
     * @param items       대상 리스트
     * @param groupKeyFn  그룹 기준 추출 함수
     * @return            그룹별로 하나씩만 남은 리스트
     */
    public static <T, K> List<T> pickFirstPerGroup(List<T> items, Function<T, K> groupKeyFn) {
        Map<K, T> grouped = new LinkedHashMap<>();

        for (T item : items) {
            K key = groupKeyFn.apply(item);
            grouped.putIfAbsent(key, item);
        }

        return new ArrayList<>(grouped.values());
    }
}