package com.icommerce.backend.service;

import com.icommerce.backend.presentation.request.AddToCartRequest;
import com.icommerce.backend.presentation.response.CartResponse;

public interface CartService {
  CartResponse addToCart(AddToCartRequest request);
}
