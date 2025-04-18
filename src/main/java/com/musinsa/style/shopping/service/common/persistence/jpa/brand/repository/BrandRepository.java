package com.musinsa.style.shopping.service.common.persistence.jpa.brand.repository;

import com.musinsa.style.shopping.service.common.persistence.jpa.brand.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    /**
     * 삭제되지 않은 모든 브랜드를 조회합니다.
     *
     * @return 삭제되지 않은 브랜드 리스트
     */
    List<Brand> findAllByIsDeletedFalse();

    /**
     * 브랜드 ID로 브랜드를 조회합니다.
     *
     * @param id 브랜드 ID
     * @return 브랜드 정보
     */
    Optional<Brand> findByBrandIdAndIsDeletedFalse(Long id);
}