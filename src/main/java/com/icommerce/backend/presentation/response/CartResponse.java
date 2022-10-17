package com.icommerce.backend.presentation.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartResponse {
  private String cartId;

  private ProductResponse product;

  private Integer amount;
}
