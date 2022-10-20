package com.icommerce.backend.presentation.response;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerOrderResponse {
  private String id;
  private ZonedDateTime createdDate;
  private BigDecimal amount;
  private String customerName;
  private String address;
  private String phoneNumber;
}
