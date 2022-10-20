package com.icommerce.backend.presentation.response;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CostSummary {
  private BigDecimal totalCost;
}
