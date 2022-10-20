package com.icommerce.backend.repository.converter;

import com.icommerce.backend.domain.type.OrderStatus;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class OrderStatusConverter extends AbstractEnumConverter<OrderStatus, Integer> {

  public OrderStatusConverter() {
    super(OrderStatus.class);
  }
}
