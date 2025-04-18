package com.musinsa.style.shopping.service.common.persistence.jpa.product.repository;

import com.musinsa.style.shopping.service.common.persistence.jpa.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    /**
     * 삭제되지 않은 모든 상품을 조회합니다.
     *
     * @return 삭제되지 않은 상품 리스트
     */
    List<Product> findAllByIsDeletedFalse();

    /**
     * 삭제되지 않은 상품을 ID로 조회합니다.
     *
     * @param id 상품 ID
     * @return 삭제되지 않은 상품
     */
    Optional<Product> findByProductIdAndIsDeletedFalse(Long id);
}