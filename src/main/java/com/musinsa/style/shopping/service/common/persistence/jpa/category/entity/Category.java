package com.musinsa.style.shopping.service.common.persistence.jpa.category.entity;

import com.musinsa.style.shopping.service.common.persistence.jpa.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    private String categoryName;

    private Boolean isDeleted = false;

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }
}