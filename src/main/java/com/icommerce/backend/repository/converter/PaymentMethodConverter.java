package com.icommerce.backend.repository.converter;

import com.icommerce.backend.domain.type.PaymentMethod;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PaymentMethodConverter extends AbstractEnumConverter<PaymentMethod, Integer> {

  public PaymentMethodConverter() {
    super(PaymentMethod.class);
  }
}
