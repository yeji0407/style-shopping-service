package com.musinsa.style.shopping.service.common.persistence.jpa.product.entity;

import com.musinsa.style.shopping.service.common.persistence.jpa.brand.entity.Brand;
import com.musinsa.style.shopping.service.common.persistence.jpa.category.entity.Category;
import com.musinsa.style.shopping.service.common.persistence.jpa.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private Long price;

    private Boolean isDeleted = false;

    public Product(Brand brand, Category category, Long price) {
        this.brand = brand;
        this.category = category;
        this.price = price;
    }

    public void updatePrice(Long price) {
        this.price = price;
    }

    public void softDelete() {
        this.isDeleted = true;
    }

    public boolean isDeleted() {
        return isDeleted;
    }
}