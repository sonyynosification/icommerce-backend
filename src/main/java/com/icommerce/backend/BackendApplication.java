package com.icommerce.backend;

import com.icommerce.backend.domain.entity.Category;
import com.icommerce.backend.domain.entity.Product;
import com.icommerce.backend.domain.repository.CartProductRepository;
import com.icommerce.backend.domain.repository.CartRepository;
import com.icommerce.backend.domain.repository.CategoryRepository;
import com.icommerce.backend.domain.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class BackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(BackendApplication.class, args);
  }

  @Bean
  @Profile("init-sample")
  ApplicationRunner generateSampleData(
    CategoryRepository categoryRepository,
    ProductRepository productRepository,
    CartRepository cartRepository,
    CartProductRepository cartProductRepository
  ) {
    return args -> {
      cartProductRepository.deleteAll();
      cartRepository.deleteAll();
      productRepository.deleteAll();
      categoryRepository.deleteAll();

      final var categories = List.of(
          Category.builder().name("clothing").build(),
          Category.builder().name("footwear").build(),
          Category.builder().name("glasses").build()
      );
      categoryRepository.saveAll(categories);

      final var products = List.of(
          Product.builder()
              .name("Shirt A1")
              .sku("S00A1W")
              .categoryId(categories.get(0).getId())
              .brand("Uniqlo")
              .color("white")
              .price(BigDecimal.valueOf(200_000))
              .build(),
          Product.builder()
              .name("Shirt A1")
              .sku("S00A1R")
              .categoryId(categories.get(0).getId())
              .brand("Uniqlo")
              .color("red")
              .price(BigDecimal.valueOf(220_000))
              .build(),
          Product.builder()
              .name("Nike Air 270")
              .sku("NK001B")
              .categoryId(categories.get(1).getId())
              .brand("Nike")
              .color("black")
              .price(BigDecimal.valueOf(3_500_000))
              .build()
      );
      productRepository.saveAll(products);
    };
  }
}
