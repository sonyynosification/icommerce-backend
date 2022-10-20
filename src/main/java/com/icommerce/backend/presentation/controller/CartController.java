package com.icommerce.backend.presentation.controller;

import com.icommerce.backend.domain.exception.InvalidCartException;
import com.icommerce.backend.domain.exception.InvalidProductException;
import com.icommerce.backend.presentation.request.AddToCartRequest;
import com.icommerce.backend.presentation.request.CheckoutRequest;
import com.icommerce.backend.presentation.request.UpdateCartRequest;
import com.icommerce.backend.presentation.response.CartResponse;
import com.icommerce.backend.presentation.utils.ErrorUtils;
import com.icommerce.backend.service.CartService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
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
  public ResponseEntity<Object> addToCart(@Valid @RequestBody AddToCartRequest request) {
    try {
      final var cart = cartService.addToCart(request);
      return ResponseEntity.ok(cart);
    } catch (InvalidCartException e) {
      return ResponseEntity.notFound()
          .build();
    } catch (InvalidProductException e) {
      return ResponseEntity.badRequest()
          .body(ErrorUtils.createError(HttpStatus.BAD_REQUEST, e.getMessage()))
          ;
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> updateCart(
      @PathVariable("id") String cartId,
      @Valid @RequestBody UpdateCartRequest request) {
    try {
      final var cart = cartService.updateCart(cartId, request);
      return ResponseEntity.ok(cart);
    } catch (InvalidCartException e) {
      return ResponseEntity.notFound().build();
    } catch (InvalidProductException e) {
      return ResponseEntity.badRequest()
          .body(ErrorUtils.createError(HttpStatus.BAD_REQUEST, e.getMessage()))
          ;
    }
  }

  @PostMapping("/checkout/{id}")
  public ResponseEntity<Object> checkout(@PathVariable("id") String cartId,
      @Valid @RequestBody CheckoutRequest request) {
    try {
      cartService.checkout(cartId, request);
      return ResponseEntity.ok().build();
    } catch (InvalidCartException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<CartResponse> getCartDetails(@PathVariable("id") String cartId) {
    try {
      return ResponseEntity.ok(cartService.getDetails(cartId));
    } catch (InvalidCartException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
