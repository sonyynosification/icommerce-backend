package com.icommerce.backend.service.mapper;

import com.icommerce.backend.domain.entity.Cart;
import com.icommerce.backend.presentation.request.CheckoutRequest;
import com.icommerce.backend.presentation.response.CartResponse;
import java.util.UUID;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CartMapper {

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
      nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
  void updateDetails(CheckoutRequest request, @MappingTarget Cart entity);

  @Mapping(target = "products", ignore = true)
  @Mapping(target = "cartId", source = "id")
  CartResponse toResponse(Cart cart);

  default String stringToUUID(UUID uuid) {
    return uuid == null ? null : uuid.toString();
  }
}
