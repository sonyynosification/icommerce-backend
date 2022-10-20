package com.icommerce.backend.presentation.request;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchProductRequest implements SearchRequest {
  private Integer page;
  private Integer size;
  private List<String> sort;

  @Override
  public int getPage() {
    return page != null ? page : SearchRequest.super.getPage();
  }

  @Override
  public int getSize() {
    return size != null ? size : SearchRequest.super.getSize();
  }

  @Override
  public List<String> getSort() {
    return sort != null ? sort : SearchRequest.super.getSort();
  }

  private String name;
  private Integer categoryId;
  private String brand;
  private String color;
  private BigDecimal maxPrice;
  private BigDecimal minPrice;
}
