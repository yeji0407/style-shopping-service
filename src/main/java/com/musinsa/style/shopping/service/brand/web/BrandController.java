package com.musinsa.style.shopping.service.brand.web;

import com.musinsa.style.shopping.service.brand.message.BrandCreateRequest;
import com.musinsa.style.shopping.service.brand.message.BrandDeleteResponse;
import com.musinsa.style.shopping.service.brand.message.BrandResponse;
import com.musinsa.style.shopping.service.brand.message.BrandUpdateRequest;
import com.musinsa.style.shopping.service.brand.service.BrandService;
import com.musinsa.style.shopping.service.common.message.CommonResponse;
import com.musinsa.style.shopping.service.common.persistence.jpa.brand.entity.Brand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/brand")
public class BrandController {

    private final BrandService brandService;

    /**
     * 삭제되지 않은 모든 브랜드 조회
     */
    @GetMapping("/list")
    public CommonResponse<List<BrandResponse>> getAllBrands() {
        return CommonResponse.success(brandService.getAllBrands());
    }

    /**
     * 브랜드 생성
     *
     * @param request 브랜드 생성 요청
     * @return 생성된 브랜드 정보
     */
    @PostMapping("/create")
    public CommonResponse<BrandResponse> create(@RequestBody @Valid BrandCreateRequest request) {
        Brand brand = brandService.create(request.name());

        return CommonResponse.success(BrandResponse.from(brand));
    }

    /**
     * 브랜드 수정
     *
     * @param id 브랜드 ID
     * @return 수정된 브랜드 정보
     */
    @PostMapping("/update/{id}")
    public CommonResponse<BrandResponse> update(@PathVariable Long id,
                                                @RequestBody @Valid BrandUpdateRequest request) {
        return CommonResponse.success(
                BrandResponse.from(brandService.update(id, request.name()))
        );
    }

    /**
     * 브랜드 삭제
     *
     * @param id 브랜드 ID
     * @return 삭제된 브랜드 ID
     */
    @PostMapping("/delete/{id}")
    public CommonResponse<BrandDeleteResponse> delete(@PathVariable Long id) {
        brandService.delete(id);

        return CommonResponse.success(new BrandDeleteResponse(id));
    }
}