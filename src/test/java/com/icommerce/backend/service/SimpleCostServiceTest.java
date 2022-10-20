package com.icommerce.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.icommerce.backend.DataUtils;
import com.icommerce.backend.presentation.response.CostSummary;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

class SimpleCostServiceTest {

  private final SimpleCostService costService = new SimpleCostService();

  @Test
  void summaryCost() {
    final CostSummary cost = costService.findCost(List.of(
        DataUtils.generateCartProduct(1L, 10, BigDecimal.valueOf(100_000)),
        DataUtils.generateCartProduct(2L, 20, BigDecimal.valueOf(10_000))
    ));
    assertEquals(BigDecimal.valueOf(1_200_000), cost.getTotalCost());
  }
}
