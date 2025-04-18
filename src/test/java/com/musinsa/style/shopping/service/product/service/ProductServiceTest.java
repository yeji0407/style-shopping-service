package com.musinsa.style.shopping.service.product.service;

import com.musinsa.style.shopping.service.brand.message.BrandResponse;
import com.musinsa.style.shopping.service.brand.service.BrandService;
import com.musinsa.style.shopping.service.category.service.CategoryService;
import com.musinsa.style.shopping.service.common.code.ErrorCode;
import com.musinsa.style.shopping.service.common.exception.BusinessException;
import com.musinsa.style.shopping.service.common.persistence.jpa.brand.entity.Brand;
import com.musinsa.style.shopping.service.common.persistence.jpa.brand.repository.BrandRepository;
import com.musinsa.style.shopping.service.common.persistence.jpa.category.entity.Category;
import com.musinsa.style.shopping.service.common.persistence.jpa.category.repository.CategoryRepository;
import com.musinsa.style.shopping.service.common.persistence.jpa.product.entity.Product;
import com.musinsa.style.shopping.service.common.persistence.jpa.product.repository.ProductRepository;
import com.musinsa.style.shopping.service.product.dto.ProductDetailInfo;
import com.musinsa.style.shopping.service.product.message.BrandLowestPriceResponse;
import com.musinsa.style.shopping.service.product.message.CategoryPriceRangeResponse;
import com.musinsa.style.shopping.service.product.message.LowestPriceResponse;
import com.musinsa.style.shopping.service.product.message.ProductResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    @Mock
    private BrandService brandService;

    @Mock
    private CategoryService categoryService;

    @Test
    @DisplayName("카테고리별 최저가 브랜드 조회 성공 테스트")
    void getLowestPricesByCategory_returnsTotalAndList() {
        List<ProductDetailInfo> mockItems = List.of(
                new ProductDetailInfo(null, 1L, "상의", 2L, "B", 10000L)
        );

        when(productRepository.findLowestPriceByCategory()).thenReturn(mockItems);

        LowestPriceResponse response = productService.getLowestPricesByCategory();

        assertThat(response.products()).hasSize(1);
        assertThat(response.totalPrice()).isEqualTo(10000L);
    }

    @Test
    @DisplayName("단일 브랜드 총액 최저가의 카테고리별 상품 조회 성공 테스트")
    void getBrandLowestPrice_success() {
        // Given
        List<ProductDetailInfo> products = List.of(
                new ProductDetailInfo(1L, 1L, "상의", 1L, "브랜드A",  10_000L),
                new ProductDetailInfo(2L, 2L, "아우터", 1L, "브랜드A",  5_000L)
        );

        Map<Long, String> categoryMap = Map.of(
                1L, "상의",
                2L, "아우터"
        );

        given(productRepository.findBrandWithLowestTotalPrice()).willReturn(products);
        given(categoryService.getCategoryNameMap()).willReturn(categoryMap);
        given(brandService.getBrandById(1L)).willReturn(new BrandResponse(1L, "브랜드A"));

        // When
        BrandLowestPriceResponse response = productService.getBrandLowestPrice();

        // Then
        assertThat(response.brandName()).isEqualTo("브랜드A");
        assertThat(response.categories()).hasSize(2);
        assertThat(response.totalPrice()).isEqualTo(15_000L);

        assertThat(response.categories())
                .extracting("categoryName")
                .containsExactlyInAnyOrder("상의", "아우터");

        assertThat(response.categories())
                .extracting("price")
                .containsExactlyInAnyOrder(10_000L, 5_000L);
    }


    @Test
    @DisplayName("카테고리 별 최저가와 최고가 상품 조회 성공 테스트")
    void getCategoryPriceRange_success() {
        // Given
        String categoryName = "아우터";

        List<ProductDetailInfo> products = List.of(
                new ProductDetailInfo(1L,  1L, "아우터", 2L, "브랜드2",  5100L),  // 최저가
                new ProductDetailInfo(2L, 1L, "아우터", 5L, "브랜드5", 7200L)   // 최고가
        );

        given(productRepository.findByCategoryOrderByPrice(categoryName)).willReturn(products);

        // When
        CategoryPriceRangeResponse response = productService.getCategoryPriceRange(categoryName);

        // Then
        assertThat(response.categoryName()).isEqualTo("아우터");

        assertThat(response.lowest().brandName()).isEqualTo("브랜드2");
        assertThat(response.lowest().price()).isEqualTo(5100L);

        assertThat(response.highest().brandName()).isEqualTo("브랜드5");
        assertThat(response.highest().price()).isEqualTo(7200L);
    }

    @Test
    @DisplayName("상품 목록 조회 성공 테스트")
    void test_getAll_success() {
        // given
        Brand brand = new Brand("A");
        Category category = new Category("상의");
        Product product = new Product(brand, category, 11200L);
        ReflectionTestUtils.setField(product, "productId", 1L);

        when(productRepository.findAllByIsDeletedFalse())
                .thenReturn(List.of(product));

        // when
        List<ProductResponse> result = productService.getAll();

        // then
        assertEquals(1, result.size());
        assertEquals("A", result.getFirst().brandName());
        assertEquals("상의", result.getFirst().categoryName());
        assertEquals(11200L, result.getFirst().price());
    }

    @Test
    @DisplayName("상품 생성 성공")
    void test_create_success() {
        // Given
        Long brandId = 1L;
        Long categoryId = 10L;
        Long price = 15000L;

        Brand brand = new Brand("A");
        Category category = new Category("상의");

        ReflectionTestUtils.setField(brand, "brandId", brandId);
        ReflectionTestUtils.setField(category, "categoryId", categoryId);

        given(brandRepository.findByBrandIdAndIsDeletedFalse(brandId)).willReturn(Optional.of(brand));
        given(categoryRepository.findByCategoryIdAndIsDeletedFalse(categoryId)).willReturn(Optional.of(category));

        Product savedProduct = new Product(brand, category, price);
        ReflectionTestUtils.setField(savedProduct, "productId", 100L);

        given(productRepository.save(any(Product.class))).willReturn(savedProduct);

        // When
        Product result = productService.create(brandId, categoryId, price);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getPrice()).isEqualTo(price);
        assertThat(result.getBrand().getBrandId()).isEqualTo(brandId);
        assertThat(result.getCategory().getCategoryId()).isEqualTo(categoryId);

        verify(productRepository).save(any(Product.class));
    }

    @Test
    @DisplayName("존재하지 않는 브랜드로 상품 생성 시 예외 발생")
    void test_create_fail_brand_not_found() {
        Long brandId = 999L;
        Long categoryId = 1L;
        Long price = 10000L;

        given(brandRepository.findByBrandIdAndIsDeletedFalse(brandId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> productService.create(brandId, categoryId, price))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.ENTITY_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("존재하지 않는 카테고리로 상품 생성 시 예외 발생")
    void test_create_fail_category_not_found() {
        Long brandId = 1L;
        Long categoryId = 999L;
        Long price = 10000L;

        Brand brand = new Brand("A");
        ReflectionTestUtils.setField(brand, "brandId", brandId);

        given(brandRepository.findByBrandIdAndIsDeletedFalse(brandId)).willReturn(Optional.of(brand));
        given(categoryRepository.findByCategoryIdAndIsDeletedFalse(categoryId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> productService.create(brandId, categoryId, price))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.ENTITY_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("상품 수정 성공 테스트")
    void test_update_success() {
        // given
        Product product = new Product(new Brand("A"), new Category("상의"), 10000L);
        ReflectionTestUtils.setField(product, "productId", 1L);

        when(productRepository.findByProductIdAndIsDeletedFalse(1L)).thenReturn(Optional.of(product));

        // when
        productService.update(1L, 8800L);

        // then
        assertEquals(8800L, product.getPrice());
    }

    @Test
    @DisplayName("상품 수정 실패 테스트")
    void test_update_fail() {
        when(productRepository.findByProductIdAndIsDeletedFalse(999L)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> {
            productService.update(999L, 9900L);
        });
    }

    @Test
    @DisplayName("상품 삭제 성공 테스트")
    void test_delete_success() {
        // given
        Product product = new Product(new Brand("A"), new Category("상의"), 10000L);
        ReflectionTestUtils.setField(product, "productId", 1L);

        when(productRepository.findByProductIdAndIsDeletedFalse(1L)).thenReturn(Optional.of(product));

        // when
        productService.delete(1L);

        // then
        assertTrue(product.isDeleted());
    }

    @Test
    @DisplayName("상품 삭제 실패 테스트")
    void test_delete_fail() {
        when(productRepository.findByProductIdAndIsDeletedFalse(anyLong())).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> {
            productService.delete(123L);
        });
    }
}