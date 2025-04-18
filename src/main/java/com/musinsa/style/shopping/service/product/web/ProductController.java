package com.musinsa.style.shopping.service.product.web;

import com.musinsa.style.shopping.service.common.message.CommonResponse;
import com.musinsa.style.shopping.service.common.persistence.jpa.product.entity.Product;
import com.musinsa.style.shopping.service.product.message.*;
import com.musinsa.style.shopping.service.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    /**
     * 카테고리 별 최저가격인 브랜드와 가격, 총액 조회
     */
    @GetMapping("/lowest-prices")
    public CommonResponse<LowestPriceResponse> getLowestPrices() {
        return CommonResponse.success(productService.getLowestPricesByCategory());
    }

    /**
     * 단일 브랜드 총액 최저가의 카테고리별 상품 조회
     */
    @GetMapping("/brand-lowest-prices")
    public CommonResponse<BrandLowestPriceResponse> getLowestTotal() {
        return CommonResponse.success(productService.getBrandLowestPrice());
    }

    /**
     * 카테고리 별 최저가격인 브랜드와 가격, 최고가격인 브랜드와 가격 조회
     */
    @PostMapping("/category-price-range")
    public CommonResponse<CategoryPriceRangeResponse> getCategoryPriceRange(
            @Valid @RequestBody CategoryPriceRequest request
    ) {
        return CommonResponse.success(productService.getCategoryPriceRange(request.categoryName()));
    }

    /**
     * 삭제되지 않은 모든 상품 조회
     */
    @GetMapping("/list")
    public CommonResponse<List<ProductResponse>> getAll() {
        return CommonResponse.success(productService.getAll());
    }

    /**
     * 상품 생성
     *
     * @param request 상품 생성 요청
     * @return 생성된 상품 정보
     */
    @PostMapping("/create")
    public CommonResponse<ProductResponse> create(@RequestBody @Valid ProductCreateRequest request) {
        Product result = productService.create(request.brandId(), request.categoryId(), request.price());

        return CommonResponse.success(ProductResponse.from(result));
    }

    /**
     * 상품 수정
     *
     * @param id 상품 ID
     * @return 수정된 상품 정보
     */
    @PostMapping("/update/{id}")
    public CommonResponse<ProductResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid ProductUpdateRequest request
    ) {
        Product result = productService.update(id, request.price());

        return CommonResponse.success(ProductResponse.from(result));
    }

    /**
     * 상품 삭제
     *
     * @param id 상품 ID
     * @return 삭제된 상품 ID
     */
    @PostMapping("/delete/{id}")
    public CommonResponse<ProductDeleteResponse> delete(@PathVariable Long id) {
        productService.delete(id);
        return CommonResponse.success(new ProductDeleteResponse(id));
    }
}