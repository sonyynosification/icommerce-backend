package com.icommerce.backend.presentation.controller;

import com.icommerce.backend.presentation.request.AddToCartRequest;
import com.icommerce.backend.presentation.response.CartResponse;
import com.icommerce.backend.service.CartService;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

  private final CartService cartService;

  public CartController(CartService cartService) {
    this.cartService = cartService;
  }

  @PostMapping
  public ResponseEntity<CartResponse> addToCart(@Valid @RequestBody AddToCartRequest request) {
    final var cart = cartService.addToCart(request);
    return ResponseEntity.ok(cart);
  }
}
