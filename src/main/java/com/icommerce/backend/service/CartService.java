package com.icommerce.backend.service;

import com.icommerce.backend.presentation.request.AddToCartRequest;
import com.icommerce.backend.presentation.request.CheckoutRequest;
import com.icommerce.backend.presentation.request.UpdateCartRequest;
import com.icommerce.backend.presentation.response.CartResponse;

public interface CartService {

  /**
   * Add a product to an existing cart, or to a new cart
   * @param request information of cart and product to be added to. If cartId is empty, will create a new cart.
   * @return information of created cart and its products
   * @throws com.icommerce.backend.domain.exception.InvalidCartException when existing cartId is invalid
   * @throws com.icommerce.backend.domain.exception.InvalidProductException when specified productId is invalid
   */
  CartResponse addToCart(AddToCartRequest request);

  /**
   * Update products in cart
   * @param cartId ID of cart
   * @param request details of products and their amount.
   * @return information of updated cart and its products
   * @throws com.icommerce.backend.domain.exception.InvalidCartException when existing cartId is invalid
   * @throws com.icommerce.backend.domain.exception.InvalidProductException when specified productId is invalid
   */
  CartResponse updateCart(String cartId, UpdateCartRequest request);

  /**
   * Checkout a specified cart
   * @param cartId ID of cart
   * @throws com.icommerce.backend.domain.exception.InvalidCartException when cartId is not valid
   */
  void checkout(String cartId, CheckoutRequest request);

  /**
   * Get details of a cart by ID
   * @param cartId ID of cart
   * @throws com.icommerce.backend.domain.exception.InvalidCartException when cartId is not valid
   */
  CartResponse getDetails(String cartId);
}
