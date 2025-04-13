package com.musinsa.style.shopping.service.common.persistence.jpa.product.repository;

import com.musinsa.style.shopping.service.common.persistence.jpa.brand.entity.QBrand;
import com.musinsa.style.shopping.service.common.persistence.jpa.category.entity.QCategory;
import com.musinsa.style.shopping.service.common.persistence.jpa.product.entity.QProduct;
import com.musinsa.style.shopping.service.product.dto.LowestPriceItemDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ProductRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<LowestPriceItemDto> findLowestPriceByCategory() {
        QProduct p = QProduct.product;
        QProduct pSub = new QProduct("pSub");
        QBrand b = QBrand.brand;
        QCategory c = QCategory.category;

        return queryFactory
            .select(Projections.constructor(
                LowestPriceItemDto.class,
                p.category.categoryId,
                c.categoryName,
                p.brand.brandId,
                b.brandName,
                p.price
            ))
            .from(p)
            .join(p.brand, b)
            .join(p.category, c)
            .where(p.isDeleted.eq(false)
                .and(p.price.eq(
                    JPAExpressions.select(pSub.price.min())
                        .from(pSub)
                        .where(pSub.category.categoryId.eq(p.category.categoryId)
                               .and(pSub.isDeleted.eq(false)))
                ))
            )
            .fetch();
    }
}