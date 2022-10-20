package com.icommerce.backend.service;

import com.icommerce.backend.domain.entity.CartProduct;
import com.icommerce.backend.domain.entity.Product;
import com.icommerce.backend.presentation.response.CostSummary;
import java.util.List;

public interface CostService {
  CostSummary findCost(List<CartProduct> products);
}
