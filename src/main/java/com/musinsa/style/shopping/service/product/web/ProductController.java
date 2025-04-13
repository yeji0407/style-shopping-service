package com.musinsa.style.shopping.service.product.web;

import com.musinsa.style.shopping.service.product.message.LowestPriceResponse;
import com.musinsa.style.shopping.service.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/lowest-prices")
    public ResponseEntity<LowestPriceResponse> getLowestPrices() {
        return ResponseEntity.ok(productService.getLowestPricesByCategory());
    }
}