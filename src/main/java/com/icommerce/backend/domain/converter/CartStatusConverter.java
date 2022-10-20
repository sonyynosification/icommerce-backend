package com.icommerce.backend.domain.converter;

import com.icommerce.backend.domain.type.CartStatus;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class CartStatusConverter extends AbstractEnumConverter<CartStatus, Integer> {

  public CartStatusConverter() {
    super(CartStatus.class);
  }

}
