package com.icommerce.backend.service.mapper;

import com.icommerce.backend.domain.entity.CartProduct;
import com.icommerce.backend.presentation.response.CartResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CartProductMapper {
  @Mapping(target = "product.category", ignore = true)
  CartResponse.CartProduct toResponse(CartProduct entity);
}
