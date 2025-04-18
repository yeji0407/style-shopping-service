package com.musinsa.style.shopping.service.common.persistence.jpa.product.repository;

import com.musinsa.style.shopping.service.common.code.ErrorCode;
import com.musinsa.style.shopping.service.common.exception.BusinessException;
import com.musinsa.style.shopping.service.common.persistence.jpa.brand.entity.QBrand;
import com.musinsa.style.shopping.service.common.persistence.jpa.category.entity.QCategory;
import com.musinsa.style.shopping.service.common.persistence.jpa.product.entity.QProduct;
import com.musinsa.style.shopping.service.product.dto.ProductDetailInfo;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ProductRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<ProductDetailInfo> findLowestPriceByCategory() {
        /*
           MySQL 쿼리 (H2 는 서브쿼리 내 LIMIT 사용 불가하므로 쿼리 조회 후 중복 제거)

           SELECT
                p.category_id,
                c.category_name,
                p.brand_id,
                b.brand_name,
                p.price
            FROM
                product p
            JOIN brand b ON p.brand_id = b.id
            JOIN category c ON p.category_id = c.id
            WHERE
                p.is_deleted = false
                AND p.product_id = (
                    SELECT pSub.product_id
                    FROM product pSub
                    WHERE pSub.category_id = p.category_id
                        AND pSub.is_deleted = false
                    ORDER BY pSub.price ASC
                    LIMIT 1
                )
            ORDER BY p.category_id ASC;
        **/

        QProduct p = QProduct.product;
        QProduct pSub = new QProduct("pSub");
        QBrand b = QBrand.brand;
        QCategory c = QCategory.category;

        return queryFactory
            .select(Projections.constructor(
                ProductDetailInfo.class,
                p.productId,
                p.category.categoryId,
                c.categoryName,
                p.brand.brandId,
                b.brandName,
                p.price
            ))
            .from(p)
            .join(p.brand, b)
            .join(p.category, c)
            .where(p.isDeleted.isFalse()
                    .and(p.brand.isDeleted.isFalse())
                    .and(p.category.isDeleted.isFalse())
                .and(p.price.eq(
                    JPAExpressions.select(pSub.price.min())
                        .from(pSub)
                        .where(pSub.category.categoryId.eq(p.category.categoryId)
                               .and(pSub.isDeleted.isFalse())
                                .and(pSub.brand.isDeleted.isFalse())
                                .and(pSub.category.isDeleted.isFalse()))
                ))
            )
                .orderBy(p.category.categoryId.asc())
            .fetch();
    }

    @Override
    public List<ProductDetailInfo> findBrandWithLowestTotalPrice() {
        /*
           MySQL 쿼리 (QueryDSL 은 한번에 처리 불가)

           WITH brand_category_price AS (
              SELECT
                brand_id,
                category_id,
                MIN(price) AS min_price
              FROM product
              WHERE is_deleted = FALSE
              GROUP BY brand_id, category_id
            ),
            brand_total_price AS (
              SELECT
                brand_id,
                SUM(min_price) AS total_price
              FROM brand_category_price
              GROUP BY brand_id
            ),
            lowest_brand AS (
              SELECT brand_id
              FROM brand_total_price
              ORDER BY total_price
              LIMIT 1
            )
            SELECT
              b.brand_id,
              b.brand_name,
              c.category_id,
              c.category_name,
              bcp.min_price,
              btp.total_price
            FROM lowest_brand lb
            JOIN brand_category_price bcp ON bcp.brand_id = lb.brand_id
            JOIN brand_total_price btp ON btp.brand_id = lb.brand_id
            JOIN category c ON c.category_id = bcp.category_id
            JOIN brand b ON b.brand_id = lb.brand_id
            ORDER BY c.category_id;
         */

        QProduct p = QProduct.product;

        // 1단계: 브랜드별 카테고리별 최저 가격
        List<Tuple> brandCategoryMinPrices = queryFactory
                .select(p.brand.brandId, p.category.categoryId, p.price.min())
                .from(p)
                .where(p.isDeleted.isFalse()
                        .and(p.brand.isDeleted.isFalse())
                        .and(p.category.isDeleted.isFalse()))
                .groupBy(p.brand.brandId, p.category.categoryId)
                .fetch();

        if (brandCategoryMinPrices.isEmpty()) {
            throw new BusinessException(ErrorCode.ENTITY_NOT_FOUND);
        }

        // 2단계: 브랜드별 총액 계산 및 최저가 브랜드 선별
        Map<Long, List<Long>> brandToPrices = brandCategoryMinPrices.stream()
                .collect(Collectors.groupingBy(
                        t -> t.get(p.brand.brandId),
                        Collectors.mapping(t -> t.get(2, Long.class), Collectors.toList())
                ));

        Long lowestPriceBrandId = brandToPrices.entrySet().stream()
                .min(Comparator.comparingLong(e -> e.getValue().stream().mapToLong(Long::longValue).sum()))
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));

        // 3단계: 해당 브랜드의 카테고리별 최저가 상품 조회
        return brandCategoryMinPrices.stream()
                .filter(t -> Objects.equals(t.get(p.brand.brandId), lowestPriceBrandId))
                .map(t -> new ProductDetailInfo(
                        null,
                        t.get(p.category.categoryId),
                        null,
                        t.get(p.brand.brandId),
                        null,
                        t.get(2, Long.class)
                ))
                .toList();

    }

    @Override
    public List<ProductDetailInfo> findByCategoryOrderByPrice(String categoryName) {
        QProduct p = QProduct.product;
        QCategory c = QCategory.category;
        QBrand b = QBrand.brand;

        List<Tuple> prices = queryFactory
                .select(b.brandName, p.price)
                .from(p)
                .join(p.brand, b)
                .join(p.category, c)
                .where(p.isDeleted.isFalse()
                        .and(b.isDeleted.isFalse())
                        .and(c.isDeleted.isFalse())
                        .and(c.categoryName.eq(categoryName)))
                .orderBy(p.price.asc())
                .fetch();

        return prices.stream()
                .map(t -> new ProductDetailInfo(
                        null,
                        null,
                        categoryName,
                        null,
                        t.get(b.brandName),
                        t.get(1, Long.class)
                ))
                .toList();
    }
}