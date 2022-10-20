package com.icommerce.backend.service;

import com.icommerce.backend.domain.entity.CartProduct;
import com.icommerce.backend.domain.entity.Product;
import com.icommerce.backend.presentation.response.CostSummary;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SimpleCostService implements CostService {

  @Override
  public CostSummary findCost(List<CartProduct> products) {
    final BigDecimal totalCost = products.stream()
        .reduce(
            BigDecimal.ZERO,
            (bigDecimal, cartProduct) -> bigDecimal.add(cartProduct.getProduct().getPrice()
                .multiply(BigDecimal.valueOf(cartProduct.getAmount()))
            ),
            BigDecimal::add
        );

    final CostSummary costSummary = new CostSummary();
    costSummary.setTotalCost(totalCost);

    return costSummary;
  }
}
