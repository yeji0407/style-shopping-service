package com.musinsa.style.shopping.service.common.persistence.jpa.product.repository;

import com.musinsa.style.shopping.service.common.persistence.jpa.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
}