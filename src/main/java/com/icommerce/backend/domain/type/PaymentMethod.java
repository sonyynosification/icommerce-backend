package com.icommerce.backend.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentMethod implements PersistableEnum<Integer> {
  CASH(1),
  ;

  private final Integer code;
}
