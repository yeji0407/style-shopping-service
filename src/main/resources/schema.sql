DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS brand;
DROP TABLE IF EXISTS category;

CREATE TABLE product (
                         product_id      BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 상품 ID (PK, Auto Increment)
                         brand_id        BIGINT NOT NULL,           -- 브랜드 ID
                         category_id     BIGINT NOT NULL,           -- 카테고리 ID
                         price           BIGINT NOT NULL,           -- 가격
                         is_deleted      BOOLEAN DEFAULT FALSE,     -- 삭제 여부
                         created_by      VARCHAR(50),                    -- 생성자 ID
                         created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 생성 일시
                         modified_by     VARCHAR(50),                    -- 수정자 ID
                         modified_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP   -- 수정 일시
);
CREATE INDEX idx_product_category_price ON product (category_id, price);
CREATE INDEX idx_product_is_deleted ON product (is_deleted);
CREATE INDEX idx_product_brand_category_price ON product (brand_id, category_id, price);
CREATE INDEX idx_product_category_price_desc ON product (category_id, price DESC);
CREATE INDEX idx_product_brand_id ON product (brand_id);

CREATE TABLE brand (
                       brand_id        BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 브랜드 ID (PK, Auto Increment)
                       brand_name      VARCHAR(100) NOT NULL,                            -- 브랜드 명
                       is_deleted      BOOLEAN DEFAULT FALSE,                            -- 삭제 여부
                       created_by      VARCHAR(50),                                           -- 생성자 ID
                       created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,              -- 생성 일시
                       modified_by     VARCHAR(50),                                           -- 수정자 ID
                       modified_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP               -- 수정 일시
);

CREATE TABLE category (
                          category_id     BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 카테고리 ID (PK, Auto Increment)
                          category_name   VARCHAR(100) NOT NULL,                            -- 카테고리 명
                          is_deleted      BOOLEAN DEFAULT FALSE,                            -- 삭제 여부
                          created_by      VARCHAR(50),                                           -- 생성자 ID
                          created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,              -- 생성 일시
                          modified_by     VARCHAR(50),                                           -- 수정자 ID
                          modified_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP               -- 수정 일시
);