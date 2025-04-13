-- 브랜드 A ~ I 등록
INSERT INTO brand (brand_name, is_deleted, created_by) VALUES
                                                           ('A', FALSE, 'INIT'),
                                                           ('B', FALSE, 'INIT'),
                                                           ('C', FALSE, 'INIT'),
                                                           ('D', FALSE, 'INIT'),
                                                           ('E', FALSE, 'INIT'),
                                                           ('F', FALSE, 'INIT'),
                                                           ('G', FALSE, 'INIT'),
                                                           ('H', FALSE, 'INIT'),
                                                           ('I', FALSE, 'INIT');

-- 카테고리 등록
INSERT INTO category (category_name, is_deleted, created_by) VALUES
                                                                 ('상의', FALSE, 'INIT'),
                                                                 ('아우터', FALSE, 'INIT'),
                                                                 ('바지', FALSE, 'INIT'),
                                                                 ('스니커즈', FALSE, 'INIT'),
                                                                 ('가방', FALSE, 'INIT'),
                                                                 ('모자', FALSE, 'INIT'),
                                                                 ('양말', FALSE, 'INIT'),
                                                                 ('액세서리', FALSE, 'INIT');

-- 상품 등록
-- 상품 데이터 (총 72개 상품)
-- 브랜드 A
INSERT INTO product (brand_id, category_id, price, is_deleted, created_by) VALUES
                                                                               (1, 1, 11200, FALSE, 'INIT'),
                                                                               (1, 2, 5500, FALSE, 'INIT'),
                                                                               (1, 3, 4200, FALSE, 'INIT'),
                                                                               (1, 4, 9000, FALSE, 'INIT'),
                                                                               (1, 5, 2000, FALSE, 'INIT'),
                                                                               (1, 6, 1700, FALSE, 'INIT'),
                                                                               (1, 7, 1800, FALSE, 'INIT'),
                                                                               (1, 8, 2300, FALSE, 'INIT');

-- 브랜드 B
INSERT INTO product (brand_id, category_id, price, is_deleted, created_by) VALUES
                                                                               (2, 1, 10500, FALSE, 'INIT'),
                                                                               (2, 2, 5900, FALSE, 'INIT'),
                                                                               (2, 3, 3800, FALSE, 'INIT'),
                                                                               (2, 4, 9100, FALSE, 'INIT'),
                                                                               (2, 5, 2100, FALSE, 'INIT'),
                                                                               (2, 6, 2000, FALSE, 'INIT'),
                                                                               (2, 7, 2000, FALSE, 'INIT'),
                                                                               (2, 8, 2200, FALSE, 'INIT');

-- 브랜드 C
INSERT INTO product (brand_id, category_id, price, is_deleted, created_by) VALUES
                                                                               (3, 1, 10000, FALSE, 'INIT'),
                                                                               (3, 2, 6200, FALSE, 'INIT'),
                                                                               (3, 3, 3300, FALSE, 'INIT'),
                                                                               (3, 4, 9200, FALSE, 'INIT'),
                                                                               (3, 5, 2200, FALSE, 'INIT'),
                                                                               (3, 6, 1900, FALSE, 'INIT'),
                                                                               (3, 7, 2200, FALSE, 'INIT'),
                                                                               (3, 8, 2100, FALSE, 'INIT');

-- 브랜드 D
INSERT INTO product (brand_id, category_id, price, is_deleted, created_by) VALUES
                                                                               (4, 1, 10100, FALSE, 'INIT'),
                                                                               (4, 2, 5100, FALSE, 'INIT'),
                                                                               (4, 3, 3000, FALSE, 'INIT'),
                                                                               (4, 4, 9500, FALSE, 'INIT'),
                                                                               (4, 5, 2500, FALSE, 'INIT'),
                                                                               (4, 6, 1500, FALSE, 'INIT'),
                                                                               (4, 7, 2400, FALSE, 'INIT'),
                                                                               (4, 8, 2000, FALSE, 'INIT');

-- 브랜드 E
INSERT INTO product (brand_id, category_id, price, is_deleted, created_by) VALUES
                                                                               (5, 1, 10700, FALSE, 'INIT'),
                                                                               (5, 2, 5000, FALSE, 'INIT'),
                                                                               (5, 3, 3800, FALSE, 'INIT'),
                                                                               (5, 4, 9900, FALSE, 'INIT'),
                                                                               (5, 5, 2300, FALSE, 'INIT'),
                                                                               (5, 6, 1800, FALSE, 'INIT'),
                                                                               (5, 7, 2100, FALSE, 'INIT'),
                                                                               (5, 8, 2100, FALSE, 'INIT');

-- 브랜드 F
INSERT INTO product (brand_id, category_id, price, is_deleted, created_by) VALUES
                                                                               (6, 1, 11200, FALSE, 'INIT'),
                                                                               (6, 2, 7200, FALSE, 'INIT'),
                                                                               (6, 3, 4000, FALSE, 'INIT'),
                                                                               (6, 4, 9300, FALSE, 'INIT'),
                                                                               (6, 5, 2100, FALSE, 'INIT'),
                                                                               (6, 6, 1600, FALSE, 'INIT'),
                                                                               (6, 7, 2300, FALSE, 'INIT'),
                                                                               (6, 8, 1900, FALSE, 'INIT');

-- 브랜드 G
INSERT INTO product (brand_id, category_id, price, is_deleted, created_by) VALUES
                                                                               (7, 1, 10500, FALSE, 'INIT'),
                                                                               (7, 2, 5800, FALSE, 'INIT'),
                                                                               (7, 3, 3900, FALSE, 'INIT'),
                                                                               (7, 4, 9000, FALSE, 'INIT'),
                                                                               (7, 5, 2200, FALSE, 'INIT'),
                                                                               (7, 6, 1700, FALSE, 'INIT'),
                                                                               (7, 7, 2100, FALSE, 'INIT'),
                                                                               (7, 8, 2000, FALSE, 'INIT');

-- 브랜드 H
INSERT INTO product (brand_id, category_id, price, is_deleted, created_by) VALUES
                                                                               (8, 1, 10800, FALSE, 'INIT'),
                                                                               (8, 2, 6300, FALSE, 'INIT'),
                                                                               (8, 3, 3100, FALSE, 'INIT'),
                                                                               (8, 4, 9700, FALSE, 'INIT'),
                                                                               (8, 5, 2100, FALSE, 'INIT'),
                                                                               (8, 6, 1600, FALSE, 'INIT'),
                                                                               (8, 7, 2000, FALSE, 'INIT'),
                                                                               (8, 8, 2000, FALSE, 'INIT');

-- 브랜드 I
INSERT INTO product (brand_id, category_id, price, is_deleted, created_by) VALUES
                                                                               (9, 1, 11400, FALSE, 'INIT'),
                                                                               (9, 2, 6700, FALSE, 'INIT'),
                                                                               (9, 3, 3200, FALSE, 'INIT'),
                                                                               (9, 4, 9500, FALSE, 'INIT'),
                                                                               (9, 5, 2400, FALSE, 'INIT'),
                                                                               (9, 6, 1700, FALSE, 'INIT'),
                                                                               (9, 7, 1700, FALSE, 'INIT'),
                                                                               (9, 8, 2400, FALSE, 'INIT');