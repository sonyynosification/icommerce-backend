package com.icommerce.backend.service.mapper;

import com.icommerce.backend.domain.entity.CustomerOrder;
import com.icommerce.backend.presentation.request.CheckoutRequest;
import com.icommerce.backend.presentation.response.CustomerOrderResponse;
import java.util.UUID;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerOrderMapper {

  CustomerOrder toEntity(CheckoutRequest request);

  CustomerOrderResponse toResponse(CustomerOrder entity);

  default String stringToUUID(UUID uuid) {
    return uuid == null ? null : uuid.toString();
  }
}
