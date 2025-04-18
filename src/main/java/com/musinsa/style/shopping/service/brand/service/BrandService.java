package com.musinsa.style.shopping.service.brand.service;

import com.musinsa.style.shopping.service.brand.message.BrandResponse;
import com.musinsa.style.shopping.service.common.code.ErrorCode;
import com.musinsa.style.shopping.service.common.config.cache.CacheSpec;
import com.musinsa.style.shopping.service.common.config.cache.annotation.EnumCacheEvict;
import com.musinsa.style.shopping.service.common.config.cache.annotation.EnumCacheable;
import com.musinsa.style.shopping.service.common.exception.BusinessException;
import com.musinsa.style.shopping.service.common.persistence.jpa.brand.entity.Brand;
import com.musinsa.style.shopping.service.common.persistence.jpa.brand.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    @EnumCacheable(CacheSpec.BRANDS)
    public List<BrandResponse> getAllBrands() {
        return brandRepository.findAllByIsDeletedFalse().stream()
                .map(BrandResponse::from)
                .toList();
    }

    @EnumCacheable(CacheSpec.BRAND)
    public BrandResponse getBrandById(Long id) {
        return brandRepository.findByBrandIdAndIsDeletedFalse(id)
                .map(BrandResponse::from)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));
    }

    @EnumCacheEvict(value = CacheSpec.BRANDS, allEntries = true)
    @Transactional
    public Brand create(String name) {
        try {
            return brandRepository.save(new Brand(name));
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.BRAND_CREATE_FAIL);
        }
    }

    @EnumCacheEvict(CacheSpec.BRAND)
    @EnumCacheEvict(value = CacheSpec.BRANDS, allEntries = true)
    @Transactional
    public Brand update(Long id, String newName) {
        Brand brand = brandRepository.findByBrandIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));

        brand.update(newName);
        return brandRepository.save(brand);
    }

    @EnumCacheEvict(CacheSpec.BRAND)
    @EnumCacheEvict(value = CacheSpec.BRANDS, allEntries = true)
    @Transactional
    public void delete(Long id) {
        Brand brand = brandRepository.findByBrandIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));

        brand.softDelete();
        brandRepository.save(brand);
    }
}
