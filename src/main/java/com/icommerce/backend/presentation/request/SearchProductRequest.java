package com.icommerce.backend.presentation.request;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchProductRequest implements SearchRequest {
  private String name;
  private Integer categoryId;
  private Integer brandId;
  private String color;
  private BigDecimal maxPrice;
  private BigDecimal minPrice;
}
