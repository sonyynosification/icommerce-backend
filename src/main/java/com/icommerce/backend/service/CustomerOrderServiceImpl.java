package com.icommerce.backend.service;

import com.icommerce.backend.domain.entity.Cart;
import com.icommerce.backend.domain.entity.CartProduct;
import com.icommerce.backend.domain.entity.CustomerOrder;
import com.icommerce.backend.domain.type.OrderStatus;
import com.icommerce.backend.presentation.request.CheckoutRequest;
import com.icommerce.backend.presentation.response.CostSummary;
import com.icommerce.backend.repository.persistence.CustomerOrderRepository;
import com.icommerce.backend.service.mapper.CustomerOrderMapper;
import java.math.BigDecimal;
import java.util.List;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {

  private final CustomerOrderRepository customerOrderRepository;
  private final CustomerOrderMapper customerOrderMapper;
  private final CostService costService;

  public CustomerOrderServiceImpl(CustomerOrderRepository customerOrderRepository,
      CustomerOrderMapper customerOrderMapper,
      CostService costService) {
    this.customerOrderRepository = customerOrderRepository;
    this.customerOrderMapper = customerOrderMapper;
    this.costService = costService;
  }

  @Override
  public CustomerOrder createOrder(@NonNull Cart cart, @NonNull CheckoutRequest request,
      @NonNull BigDecimal amount) {
    final CustomerOrder order = customerOrderMapper.toEntity(request);
    order.setCartId(cart.getId());
    order.setAmount(amount);
    order.setStatus(OrderStatus.PROCESSING);

    return customerOrderRepository.save(order);
  }
}
