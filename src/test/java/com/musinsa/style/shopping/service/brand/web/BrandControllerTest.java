package com.musinsa.style.shopping.service.brand.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.style.shopping.service.brand.message.BrandCreateRequest;
import com.musinsa.style.shopping.service.brand.message.BrandUpdateRequest;
import com.musinsa.style.shopping.service.common.persistence.jpa.brand.entity.Brand;
import com.musinsa.style.shopping.service.common.persistence.jpa.brand.repository.BrandRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@EnableCaching
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Sql(
        scripts = {"/schema.sql", "/data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class BrandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BrandRepository brandRepository;

    @Test
    @DisplayName("브랜드 목록 조회 API 통합 테스트")
    void test_getBrandList_success() throws Exception {
        mockMvc.perform(get("/api/brand/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(9)) // 브랜드 9개
                .andExpect(jsonPath("$.data[0].name").value("A"));
    }

    @Test
    @DisplayName("브랜드 생성 API 통합 테스트")
    void test_createBrand_success() throws Exception {
        BrandCreateRequest req = new BrandCreateRequest("C");

        mockMvc.perform(post("/api/brand/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.name").value("C"));
    }

    @Test
    @DisplayName("브랜드 수정 API 통합 테스트")
    void test_updateBrand_success() throws Exception {
        // Given
        Brand brand = brandRepository.save(new Brand("OldBrand"));
        Long id = brand.getBrandId();

        BrandUpdateRequest request = new BrandUpdateRequest("NewBrand");

        // When & Then
        mockMvc.perform(post("/api/brand/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(id))
                .andExpect(jsonPath("$.data.name").value("NewBrand"));
    }

    @Test
    @DisplayName("브랜드 수정 API 통합 테스트 - 브랜드 미존재")
    void test_updateBrand_notFound() throws Exception {
        // When & Then
        BrandUpdateRequest request = new BrandUpdateRequest("NewBrand");

        mockMvc.perform(post("/api/brand/update/{id}", 9999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("COMMON_404"));
    }

    @Test
    @DisplayName("브랜드 삭제 API 통합 테스트")
    void test_deleteBrand_success() throws Exception {
        // Given
        Brand brand = brandRepository.save(new Brand("ToDelete"));
        Long id = brand.getBrandId();

        // When & Then
        mockMvc.perform(post("/api/brand/delete/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(id));
    }

    @Test
    @DisplayName("브랜드 삭제 API 통합 테스트 - 브랜드 미존재")
    void test_deleteBrand_notFound() throws Exception {
        mockMvc.perform(post("/api/brand/delete/{id}", 123456L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("COMMON_404"));
    }
}