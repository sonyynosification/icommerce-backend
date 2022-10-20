package com.icommerce.backend.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CartStatus implements PersistableEnum<Integer> {
  NEW(0),
  PROCESSING(1),
  COMPLETED(2),
  ;

  private final Integer code;
}
