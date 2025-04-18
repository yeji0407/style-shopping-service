package com.musinsa.style.shopping.service.brand.service;

import com.musinsa.style.shopping.service.brand.message.BrandResponse;
import com.musinsa.style.shopping.service.common.exception.BusinessException;
import com.musinsa.style.shopping.service.common.persistence.jpa.brand.entity.Brand;
import com.musinsa.style.shopping.service.common.persistence.jpa.brand.repository.BrandRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BrandServiceTest {

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private BrandService brandService;

    @Test
    @DisplayName("브랜드 조회 성공 테스트")
    void getAllBrands_success() {
        List<Brand> brands = List.of(
                new Brand("A_TEST"),
                new Brand("B_TEST")
        );
        ReflectionTestUtils.setField(brands.get(0), "brandId", 1L);
        ReflectionTestUtils.setField(brands.get(1), "brandId", 2L);

        // given
        when(brandRepository.findAllByIsDeletedFalse()).thenReturn(brands);

        // when
        List<BrandResponse> result = brandService.getAllBrands();

        // then
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).id());
        assertEquals("A_TEST", result.get(0).name());
        assertEquals(2L, result.get(1).id());
        assertEquals("B_TEST", result.get(1).name());

        verify(brandRepository, times(1)).findAllByIsDeletedFalse();
    }

    @Test
    @DisplayName("브랜드 생성 성공 테스트")
    void create_success() {
        // given
        Brand brand = new Brand("Musinsa");
        ReflectionTestUtils.setField(brand, "brandId", 10L);

        when(brandRepository.save(any(Brand.class))).thenReturn(brand);

        // when
        Brand result = brandService.create("Musinsa");

        // then
        assertNotNull(result);
        assertEquals("Musinsa", result.getBrandName());
        assertEquals(10L, result.getBrandId());
        verify(brandRepository, times(1)).save(any(Brand.class));
    }

    @Test
    @DisplayName("브랜드 수정 성공 테스트")
    void update_success() {
        // given
        Brand brand = new Brand("OldName");
        ReflectionTestUtils.setField(brand, "brandId", 5L);
        when(brandRepository.findByBrandIdAndIsDeletedFalse(5L)).thenReturn(Optional.of(brand));

        // when
        brandService.update(5L, "NewName");

        // then
        assertEquals("NewName", brand.getBrandName());
        verify(brandRepository).findByBrandIdAndIsDeletedFalse(5L);
    }

    @Test
    @DisplayName("브랜드 수정 실패 테스트")
    void update_brand_fail() {
        // given
        when(brandRepository.findByBrandIdAndIsDeletedFalse(999L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(BusinessException.class, () -> {
            brandService.update(999L, "Name");
        });

        verify(brandRepository).findByBrandIdAndIsDeletedFalse(999L);
    }

    @Test
    @DisplayName("브랜드 삭제 성공 테스트")
    void delete_success() {
        // given
        Brand brand = new Brand("BrandToDelete");
        ReflectionTestUtils.setField(brand, "brandId", 7L);
        when(brandRepository.findByBrandIdAndIsDeletedFalse(7L)).thenReturn(Optional.of(brand));

        // when
        brandService.delete(7L);

        // then
        assertTrue(brand.isDeleted());
        verify(brandRepository).findByBrandIdAndIsDeletedFalse(7L);
    }

    @Test
    @DisplayName("브랜드 삭제 실패 테스트")
    void delete_fail() {
        // given
        when(brandRepository.findByBrandIdAndIsDeletedFalse(anyLong())).thenReturn(Optional.empty());

        // when & then
        assertThrows(BusinessException.class, () -> {
            brandService.delete(123L);
        });
    }
}