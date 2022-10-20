package com.icommerce.backend.presentation.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import com.icommerce.backend.domain.exception.InvalidCartException;
import com.icommerce.backend.domain.exception.InvalidProductException;
import com.icommerce.backend.presentation.request.AddToCartRequest;
import com.icommerce.backend.presentation.request.CheckoutRequest;
import com.icommerce.backend.presentation.request.UpdateCartRequest;
import com.icommerce.backend.presentation.response.CartResponse;
import com.icommerce.backend.service.CartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {
  @Mock
  private CartService cartService;

  @InjectMocks
  private CartController cartController;

  @Test
  void addToCart_success() {
    var cart = new CartResponse();
    doReturn(cart).when(cartService).addToCart(any());
    AddToCartRequest request = new AddToCartRequest();

    final var result = cartController.addToCart(request);
    assertEquals(200, result.getStatusCodeValue());
    assertEquals(cart, result.getBody());
  }

  @Test
  void addToCart_whenCartNotFound() {
    doThrow(new InvalidCartException("")).when(cartService).addToCart(any());
    AddToCartRequest request = new AddToCartRequest();
    final var result = cartController.addToCart(request);
    assertEquals(404, result.getStatusCodeValue());
  }

  @Test
  void addToCart_whenProductNotFound() {
    doThrow(new InvalidProductException("")).when(cartService).addToCart(any());
    AddToCartRequest request = new AddToCartRequest();
    final var result = cartController.addToCart(request);
    assertEquals(400, result.getStatusCodeValue());
  }

  @Test
  void updateCart_success() {
    var cart = new CartResponse();
    doReturn(cart).when(cartService).updateCart(anyString(), any());
    var request = new UpdateCartRequest();

    final var result = cartController.updateCart("1", request);
    assertEquals(200, result.getStatusCodeValue());
    assertEquals(cart, result.getBody());
  }

  @Test
  void updateCart_whenCartNotFound() {
    doThrow(new InvalidCartException("")).when(cartService).updateCart(anyString(), any());
    var request = new UpdateCartRequest();
    final var result = cartController.updateCart("1", request);
    assertEquals(404, result.getStatusCodeValue());
  }

  @Test
  void updateCart_whenProductNotFound() {
    doThrow(new InvalidProductException("")).when(cartService).updateCart(anyString(), any());
    var request = new UpdateCartRequest();
    final var result = cartController.updateCart("1", request);
    assertEquals(400, result.getStatusCodeValue());
  }

  @Test
  void getDetails_success() {
    var cart = new CartResponse();
    doReturn(cart).when(cartService).getDetails(anyString());
    final var result = cartController.getCartDetails("1");
    assertEquals(200, result.getStatusCodeValue());
    assertEquals(cart, result.getBody());
  }

  @Test
  void getDetails_notFound() {
    doThrow(new InvalidCartException("")).when(cartService).getDetails(anyString());
    final var result = cartController.getCartDetails("1");
    assertEquals(404, result.getStatusCodeValue());
  }

  @Test
  void checkout_success() {
    var result = cartController.checkout("1", new CheckoutRequest());
    assertEquals(200, result.getStatusCodeValue());
  }

  @Test
  void checkout_cartNotFound() {
    doThrow(new InvalidCartException("")).when(cartService).checkout(anyString(), any());
    final var result = cartController.checkout("1", null);
    assertEquals(404, result.getStatusCodeValue());
  }
}
