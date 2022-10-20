package com.icommerce.backend.presentation.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

import com.icommerce.backend.presentation.request.SearchProductRequest;
import com.icommerce.backend.presentation.response.ProductResponse;
import com.icommerce.backend.service.ProductService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {
  @Mock
  private ProductService productService;

  @InjectMocks
  private ProductController productController;

  @Test
  void search() {
    SearchProductRequest request = new SearchProductRequest();
    doReturn(Page.empty()).when(productService).search(request);
    final var result = productController.search(request);
    assertEquals(Page.empty(), result);
  }

  @Test
  void getDetail() {
    var product = new ProductResponse();
    doReturn(Optional.of(product)).when(productService).findById(1L);
    final var response = productController.getDetail(1L);
    assertEquals(200, response.getStatusCode().value());
  }

  @Test
  void getDetail_whenNotFound() {
    doReturn(Optional.empty()).when(productService).findById(1L);
    final var response = productController.getDetail(1L);
    assertEquals(404, response.getStatusCode().value());
  }
}