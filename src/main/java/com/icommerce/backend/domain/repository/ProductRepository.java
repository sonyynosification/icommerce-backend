package com.icommerce.backend.domain.repository;

import com.icommerce.backend.domain.entity.Product;
import com.querydsl.core.types.Predicate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.lang.NonNull;

public interface ProductRepository extends JpaRepository<Product, Long>,
    QuerydslPredicateExecutor<Product> {

  @Override
  @EntityGraph(attributePaths = {"category"})
  Optional<Product> findById(@NonNull Long productId);

  @Override
  @EntityGraph(attributePaths = {"category"})
  Page<Product> findAll(Predicate predicate, Pageable pageable);

  @Override
  @EntityGraph(attributePaths = {"category"})
  Page<Product> findAll(Pageable pageable);
}
