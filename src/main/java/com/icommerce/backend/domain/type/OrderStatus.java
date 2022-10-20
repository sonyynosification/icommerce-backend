package com.icommerce.backend.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus implements PersistableEnum<Integer> {
  PROCESSING(1),
  COMPLETED(2),
  CANCELLED(3),
  ;

  private final Integer code;
}
