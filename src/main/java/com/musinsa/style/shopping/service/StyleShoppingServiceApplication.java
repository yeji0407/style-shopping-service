package com.musinsa.style.shopping.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class StyleShoppingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StyleShoppingServiceApplication.class, args);
	}

}
