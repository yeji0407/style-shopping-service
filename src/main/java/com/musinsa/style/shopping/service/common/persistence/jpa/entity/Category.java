package com.musinsa.style.shopping.service.common.persistence.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    private String categoryName;

    private Boolean isDeleted = false;

    private String createdBy;

    private LocalDateTime createdAt = LocalDateTime.now();

    private String modifiedBy;

    private LocalDateTime modifiedAt = LocalDateTime.now();
}