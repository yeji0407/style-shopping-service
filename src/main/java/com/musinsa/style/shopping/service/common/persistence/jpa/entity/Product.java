package com.musinsa.style.shopping.service.common.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {

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

    private String createdBy;

    private LocalDateTime createdAt = LocalDateTime.now();

    private String modifiedBy;

    private LocalDateTime modifiedAt = LocalDateTime.now();
}