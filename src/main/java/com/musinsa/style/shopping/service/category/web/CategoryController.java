package com.musinsa.style.shopping.service.category.web;

import com.musinsa.style.shopping.service.category.message.CategoryResponse;
import com.musinsa.style.shopping.service.category.service.CategoryService;
import com.musinsa.style.shopping.service.common.message.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 삭제되지 않은 모든 카테고리 조회
     *
     * @return 삭제되지 않은 카테고리 리스트
     */
    @GetMapping("/list")
    public CommonResponse<List<CategoryResponse>>getCategories() {
        return CommonResponse.success(categoryService.getAllCategories());
    }
}