package com.icommerce.backend.presentation.controller;

import com.icommerce.backend.presentation.request.SearchProductRequest;
import com.icommerce.backend.presentation.response.ProductResponse;
import com.icommerce.backend.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public Page<ProductResponse> search(SearchProductRequest request) {
    return productService.search(request);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductResponse> getDetail(@PathVariable("id") Long productId) {
    return productService.findById(productId)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
