package com.musinsa.style.shopping.service.product.service;

import com.musinsa.style.shopping.service.brand.service.BrandService;
import com.musinsa.style.shopping.service.category.service.CategoryService;
import com.musinsa.style.shopping.service.common.code.ErrorCode;
import com.musinsa.style.shopping.service.common.config.cache.CacheSpec;
import com.musinsa.style.shopping.service.common.config.cache.annotation.EnumCacheEvict;
import com.musinsa.style.shopping.service.common.config.cache.annotation.EnumCacheable;
import com.musinsa.style.shopping.service.common.exception.BusinessException;
import com.musinsa.style.shopping.service.common.persistence.jpa.brand.entity.Brand;
import com.musinsa.style.shopping.service.common.persistence.jpa.brand.repository.BrandRepository;
import com.musinsa.style.shopping.service.common.persistence.jpa.category.entity.Category;
import com.musinsa.style.shopping.service.common.persistence.jpa.category.repository.CategoryRepository;
import com.musinsa.style.shopping.service.common.persistence.jpa.product.entity.Product;
import com.musinsa.style.shopping.service.common.persistence.jpa.product.repository.ProductRepository;
import com.musinsa.style.shopping.service.common.util.GroupingUtils;
import com.musinsa.style.shopping.service.product.dto.ProductDetailInfo;
import com.musinsa.style.shopping.service.product.message.*;
import com.musinsa.style.shopping.service.product.message.element.BrandLowestPriceCategory;
import com.musinsa.style.shopping.service.product.message.element.CategoryPriceRangeProduct;
import com.musinsa.style.shopping.service.product.message.element.LowestPriceProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    private final CategoryService categoryService;
    private final BrandService brandService;

    /**
     * 카테고리별 최저가 상품을 조회
     *
     * @return 최저가 상품 리스트
     */
    public LowestPriceResponse getLowestPricesByCategory() {
        List<ProductDetailInfo> products = productRepository.findLowestPriceByCategory();

        if (products.isEmpty()) {
            throw new BusinessException(ErrorCode.ENTITY_NOT_FOUND);
        }

        List<ProductDetailInfo> distinctProducts = GroupingUtils.pickFirstPerGroup(products, ProductDetailInfo::categoryId);
        long total = distinctProducts.stream().mapToLong(ProductDetailInfo::price).sum();

        List<LowestPriceProduct> items = distinctProducts.stream().map(LowestPriceProduct::from).toList();

        return new LowestPriceResponse(items, total);
    }

    /**
     * 단일 브랜드 총액 최저가의 카테고리별 상품 조회
     *
     * @return 단일 브랜드 총액 최저가의 카테고리별 상품 리스트
     */
    public BrandLowestPriceResponse getBrandLowestPrice() {
        List<ProductDetailInfo> products = productRepository.findBrandWithLowestTotalPrice();

        if (products.isEmpty()) {
            throw new BusinessException(ErrorCode.ENTITY_NOT_FOUND);
        }

        Map<Long, String> categoryNameMap = categoryService.getCategoryNameMap();

        List<BrandLowestPriceCategory> items = products.stream()
                .map(product -> new BrandLowestPriceCategory(
                        categoryNameMap.get(product.categoryId()),
                        product.price()))
                .toList();

        String brandName = brandService.getBrandById(products.getFirst().brandId()).name();
        long total = products.stream().mapToLong(ProductDetailInfo::price).sum();

        return new BrandLowestPriceResponse(brandName, items, total);
    }

    /**
     * 카테고리 별 최저가와 최고가 상품 조회
     *
     * @param categoryName 카테고리 이름
     * @return 카테고리 별 최저가와 최고가 상품 정보
     */
    public CategoryPriceRangeResponse getCategoryPriceRange(String categoryName) {
        List<ProductDetailInfo> products = productRepository.findByCategoryOrderByPrice(categoryName);

        if (products.isEmpty()) {
            throw new BusinessException(ErrorCode.ENTITY_NOT_FOUND);
        }

        return new CategoryPriceRangeResponse(
                categoryName,
                CategoryPriceRangeProduct.from(products.getFirst()),
                CategoryPriceRangeProduct.from(products.getLast())
        );
    }

    /**
     * 삭제되지 않은 모든 상품을 조회
     *
     * @return 삭제되지 않은 상품 리스트
     */
    @EnumCacheable(value = CacheSpec.PRODUCTS)
    public List<ProductResponse> getAll() {
        return productRepository.findAllByIsDeletedFalse().stream()
                .map(ProductResponse::from)
                .toList();
    }

    /**
     * 상품 생성
     *
     * @param brandId    브랜드 ID
     * @param categoryId 카테고리 ID
     * @param price      가격
     * @return 생성된 상품
     */
    @Transactional
    @EnumCacheEvict(value = CacheSpec.PRODUCTS, allEntries = true)
    public Product create(Long brandId, Long categoryId, Long price) {
        Brand brand = brandRepository.findByBrandIdAndIsDeletedFalse(brandId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));

        Category category = categoryRepository.findByCategoryIdAndIsDeletedFalse(categoryId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));

        Product product = new Product(brand, category, price);
        return productRepository.save(product);
    }

    /**
     * 상품 수정
     *
     * @param id      상품 ID
     * @param price 수정 요청 가격
     * @return 수정된 상품
     */
    @Transactional
    @EnumCacheEvict(value = CacheSpec.PRODUCTS, allEntries = true)
    public Product update(Long id, Long price) {
        Product product = productRepository.findByProductIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));

        product.updatePrice(price);
        return productRepository.save(product);
    }

    /**
     * 상품 삭제
     *
     * @param id 상품 ID
     */
    @Transactional
    @EnumCacheEvict(value = CacheSpec.PRODUCTS, allEntries = true)
    public void delete(Long id) {
        Product product = productRepository.findByProductIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));

        product.softDelete();
        productRepository.save(product);
    }
}