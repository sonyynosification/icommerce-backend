package com.icommerce.backend.presentation.response;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {
  private Integer id;
  private String name;
  private BigDecimal price;
  private String sku;
  private String color;
  private String brand;
  private CategoryResponse category;
}
