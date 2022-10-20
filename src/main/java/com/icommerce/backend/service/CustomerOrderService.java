package com.icommerce.backend.service;

import com.icommerce.backend.domain.entity.Cart;
import com.icommerce.backend.domain.entity.CustomerOrder;
import com.icommerce.backend.presentation.request.CheckoutRequest;
import java.math.BigDecimal;
import org.springframework.lang.NonNull;

public interface CustomerOrderService {
  CustomerOrder createOrder(@NonNull Cart cart, @NonNull CheckoutRequest request,
      @NonNull BigDecimal amount);
}
