package com.icommerce.backend.service;

import com.icommerce.backend.domain.entity.Product;
import com.icommerce.backend.domain.repository.ProductRepository;
import com.icommerce.backend.presentation.request.SearchProductRequest;
import com.icommerce.backend.presentation.response.ProductResponse;
import com.icommerce.backend.service.mapper.ProductMapper;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.icommerce.backend.domain.entity.QProduct;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  private final ProductMapper productMapper;

  public ProductServiceImpl(ProductRepository productRepository,
      ProductMapper productMapper) {
    this.productRepository = productRepository;
    this.productMapper = productMapper;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<ProductResponse> search(@NonNull SearchProductRequest request) {
    Assert.notNull(request, "request should not be null");
    var predicate = buildPredicate(request);

    final var products = findProducts(predicate, request.getPageable());

    return products.map(productMapper::toResponse);
  }

  private Page<Product> findProducts(Optional<BooleanExpression> predicate, Pageable pageable) {
    final Page<Product> products;
    if (predicate.isEmpty()) {
      products = productRepository.findAll(pageable);
    } else {
      products = productRepository.findAll(predicate.get(), pageable);
    }
    return products;
  }

  private Optional<BooleanExpression> buildPredicate(SearchProductRequest request) {
    return QueryBuilder.builder()
        .ifNotBlank(request.getName(), QProduct.product.name::containsIgnoreCase)
        .ifNotNull(request.getCategoryId(), QProduct.product.categoryId::eq)
        .ifNotNull(request.getBrand(), QProduct.product.brand::equalsIgnoreCase)
        .ifNotBlank(request.getColor(), QProduct.product.color::equalsIgnoreCase)
        .ifNotNull(request.getMaxPrice(), QProduct.product.price::loe)
        .ifNotNull(request.getMinPrice(), QProduct.product.price::goe)
        .toMatchAll();
  }

  @Override
  public Optional<ProductResponse> findById(Long id) {
    return productRepository.findById(id)
        .map(productMapper::toResponse)
        ;
  }
}
