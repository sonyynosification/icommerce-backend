package com.icommerce.backend.domain.repository;

import com.icommerce.backend.domain.entity.Cart;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, UUID> {

}
