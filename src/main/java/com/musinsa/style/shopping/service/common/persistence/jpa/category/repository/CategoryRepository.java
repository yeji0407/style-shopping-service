package com.musinsa.style.shopping.service.common.persistence.jpa.category.repository;

import com.musinsa.style.shopping.service.common.persistence.jpa.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * 삭제되지 않은 카테고리를 ID로 조회합니다.
     *
     * @param id 카테고리 ID
     * @return 삭제되지 않은 카테고리
     */
    Optional<Category> findByCategoryIdAndIsDeletedFalse(Long id);

    /**
     * 삭제되지 않은 모든 카테고리를 조회합니다.
     *
     * @return 삭제되지 않은 카테고리 리스트
     */
    List<Category> findAllByIsDeletedFalse();
}