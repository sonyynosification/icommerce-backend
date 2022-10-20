package com.icommerce.backend.service.mapper;

import com.icommerce.backend.domain.entity.Category;
import com.icommerce.backend.domain.entity.Product;
import com.icommerce.backend.presentation.response.CategoryResponse;
import com.icommerce.backend.presentation.response.ProductResponse;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {
  ProductResponse toResponse(Product entity);

  CategoryResponse toResponse(Category entity);
}
