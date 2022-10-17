package com.icommerce.backend.presentation.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToCartRequest {
  private String cartId;

  @NotNull
  private Long productId;

  @Positive
  private Integer amount;
}
