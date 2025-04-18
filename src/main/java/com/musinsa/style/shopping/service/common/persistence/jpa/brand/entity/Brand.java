package com.musinsa.style.shopping.service.common.persistence.jpa.brand.entity;

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
public class Brand extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandId;

    private String brandName;

    private Boolean isDeleted = false;

    public Brand(String brandName) {
        this.brandName = brandName;
    }

    public void update(String newName) {
        this.brandName = newName;
    }

    public void softDelete() {
        this.isDeleted = true;
    }

    public boolean isDeleted() {
        return isDeleted;
    }
}