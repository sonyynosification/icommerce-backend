package com.icommerce.backend.presentation.controller;

import com.icommerce.backend.presentation.request.AddToCartRequest;
import com.icommerce.backend.presentation.request.CheckoutRequest;
import com.icommerce.backend.presentation.request.UpdateCartRequest;
import com.icommerce.backend.presentation.response.CartResponse;
import com.icommerce.backend.service.CartService;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

  @PutMapping("/{id}")
  public ResponseEntity<CartResponse> updateCart(
      @PathVariable("id") String cartId,
      @Valid @RequestBody UpdateCartRequest request) {
    final var cart = cartService.updateCart(cartId, request);

    return ResponseEntity.ok(cart);
  }

  @PostMapping("/checkout/{id}")
  public void checkout(@PathVariable("id") String cartId,
      @Valid @RequestBody CheckoutRequest request) {
    cartService.checkout(cartId, request);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CartResponse> getCartDetails(@PathVariable("id") String cartId) {
    return ResponseEntity.ok(cartService.getDetails(cartId));
  }
}
