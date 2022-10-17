package com.icommerce.backend.domain.repository;

import com.icommerce.backend.domain.entity.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.lang.NonNull;

public interface ProductRepository extends JpaRepository<Product, Long>,
    QuerydslPredicateExecutor<Product> {

  @Override
  @EntityGraph(attributePaths = {"brand", "category"})
  Optional<Product> findById(@NonNull Long productId);
}
