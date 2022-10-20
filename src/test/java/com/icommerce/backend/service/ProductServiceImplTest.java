package com.icommerce.backend.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.icommerce.backend.domain.entity.Product;
import com.icommerce.backend.repository.persistence.ProductRepository;
import com.icommerce.backend.presentation.request.SearchProductRequest;
import com.icommerce.backend.presentation.response.ProductResponse;
import com.icommerce.backend.service.mapper.ProductMapper;
import com.querydsl.core.types.Predicate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

  @Mock
  private ProductRepository productRepository;
  @Spy
  private ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

  @InjectMocks
  private ProductServiceImpl productService;

  @Test
  void search_whenConditionIsEmpty() {
    SearchProductRequest request = new SearchProductRequest();

    doReturn(Page.empty()).when(productRepository).findAll(any(Pageable.class));

    final Page<ProductResponse> result = productService.search(request);

    assertTrue(result.isEmpty());
  }

  @Test
  void search_whenHavingCondition() {
    SearchProductRequest request = new SearchProductRequest();
    request.setBrand("Test");
    doReturn(Page.empty()).when(productRepository).findAll(any(Predicate.class), any(Pageable.class));

    productService.search(request);
    verify(productRepository, times(1)).findAll(any(Predicate.class), any(Pageable.class));
  }

  @Test
  void findById_whenNotFound() {
    doReturn(Optional.empty()).when(productRepository).findById(1L);
    final var result = productService.findById(1L);
    assertTrue(result.isEmpty());
  }

  @Test
  void findById_whenFoundOne() {
    doReturn(Optional.of(new Product())).when(productRepository).findById(1L);
    final var result = productService.findById(1L);
    assertTrue(result.isPresent());
  }
}