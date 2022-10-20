package com.icommerce.backend.presentation.request;

import com.icommerce.backend.domain.type.PaymentMethod;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckoutRequest {
  @NotBlank
  private String customerName;
  @NotBlank
  private String address;

  @NotBlank
  private String phoneNumber;

  @NotNull
  private PaymentMethod paymentMethod;
}
