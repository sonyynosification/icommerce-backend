package com.icommerce.backend.repository.persistence;

import com.icommerce.backend.domain.entity.CartProduct;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
  @EntityGraph(attributePaths = {"product"})
  List<CartProduct> findByCartId(UUID cartId);
}
