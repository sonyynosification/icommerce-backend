package com.icommerce.backend.repository.persistence;

import com.icommerce.backend.domain.entity.Cart;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, UUID> {

}
