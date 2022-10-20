package com.icommerce.backend.presentation.response;

import com.icommerce.backend.domain.type.CartStatus;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
  private String cartId;
  private CartStatus status;
  private List<CartProduct> products;
  private CostSummary costSummary;

  @Getter
  @Setter
  public static class CartProduct {

    private ProductResponse product;

    private Integer amount;
  }
}
