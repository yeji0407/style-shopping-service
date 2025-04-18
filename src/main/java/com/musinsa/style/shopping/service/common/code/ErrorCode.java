package com.musinsa.style.shopping.service.common.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 공통
    INTERNAL_SERVER_ERROR("COMMON_500", "서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_INPUT_VALUE("COMMON_400", "요청 값이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
    ENTITY_NOT_FOUND("COMMON_404", "요청하신 데이터를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    // Product 관련
    PRODUCT_QUERY_FAIL("PRODUCT_001", "카테고리별 최저가 브랜드를 조회하는 데 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    // Brand 관련
    BRAND_NOT_FOUND("BRAND_001", "브랜드 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    BRAND_CREATE_FAIL("BRAND_002", "브랜드 생성에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),;

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}