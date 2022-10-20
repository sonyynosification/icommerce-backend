package com.icommerce.backend.presentation.request;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCartRequest {

  @Valid
  private List<Item> items;

  @Getter
  @Setter
  public static class Item {
    private Long productId;
    @Positive
    private Integer amount;
  }
}
