package com.musinsa.style.shopping.service.product.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.style.shopping.service.common.persistence.jpa.product.repository.ProductRepository;
import com.musinsa.style.shopping.service.product.message.ProductUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Sql(
        scripts = {"/schema.sql", "/data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("카테고리별 최저가 브랜드 조회 API 통합 테스트")
    void test_getLowestPrices() throws Exception {
        mockMvc.perform(get("/api/product/lowest-prices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.products.length()").value(8)) // 카테고리 8개
                .andExpect(jsonPath("$.data.products[0].categoryName").isNotEmpty())
                .andExpect(jsonPath("$.data.products[0].brandName").isNotEmpty())
                .andExpect(jsonPath("$.data.products[0].price").isNumber())
                .andExpect(jsonPath("$.data.totalPrice").isNumber());
    }

    @Test
    @DisplayName("카테고리별 최저가/최고가 브랜드 조회 API 통합 테스트")
    void test_getLowestTotal() throws Exception {
        mockMvc.perform(get("/api/product/brand-lowest-prices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.brandName").isNotEmpty())
                .andExpect(jsonPath("$.data.totalPrice").isNumber())
                .andExpect(jsonPath("$.data.categories.length()").value(8))
                .andExpect(jsonPath("$.data.categories[0].categoryName").isNotEmpty());
    }

    @Test
    @DisplayName("카테고리별 최저가/최고가 브랜드 조회 API 통합 테스트")
    void test_getCategoryPriceRange() throws Exception {
        mockMvc.perform(post("/api/product/category-price-range")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                Map.of("categoryName", "아우터")
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.categoryName").value("아우터"))
                .andExpect(jsonPath("$.data.lowest.brandName").isNotEmpty())
                .andExpect(jsonPath("$.data.lowest.price").isNumber())
                .andExpect(jsonPath("$.data.highest.brandName").isNotEmpty())
                .andExpect(jsonPath("$.data.highest.price").isNumber());
    }

    @Test
    @DisplayName("상품 조회 API 통합 테스트")
    void test_getAllProducts_success() throws Exception {
        mockMvc.perform(get("/api/product/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(72))
                .andExpect(jsonPath("$.data[0].brandName").value("A"))
                .andExpect(jsonPath("$.data[0].categoryName").value("상의"))
                .andExpect(jsonPath("$.data[0].price").value(11200));
    }

    @Test
    @DisplayName("상품 업데이트 API 통합 테스트")
    void test_updateProduct_success() throws Exception {
        Long id = productRepository.findAll().getFirst().getProductId();

        ProductUpdateRequest request = new ProductUpdateRequest(8800L);

        mockMvc.perform(post("/api/product/update/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(id))
                .andExpect(jsonPath("$.data.price").value(8800));
    }

    @Test
    @DisplayName("상품 업데이트 API 통합 테스트 - 상품이 존재하지 않을 경우")
    void test_updateProduct_notFound() throws Exception {
        ProductUpdateRequest request = new ProductUpdateRequest(8800L);

        mockMvc.perform(post("/api/product/update/99999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("COMMON_404"));
    }

    @Test
    @DisplayName("상품 삭제 API 통합 테스트")
    void test_deleteProduct_success() throws Exception {
        Long id = productRepository.findAll().getFirst().getProductId();

        mockMvc.perform(post("/api/product/delete/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(id));
    }

    @Test
    @DisplayName("상품 삭제 API 통합 테스트 - 상품이 존재하지 않을 경우")
    void test_deleteProduct_notFound() throws Exception {
        mockMvc.perform(post("/api/product/delete/99999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("COMMON_404"));
    }
}