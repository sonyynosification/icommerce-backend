package com.icommerce.backend;

import com.icommerce.backend.domain.entity.Cart;
import com.icommerce.backend.domain.entity.CartProduct;
import com.icommerce.backend.domain.entity.Product;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class DataUtils {
  public static Cart generateCart(String uuid) {
    Cart cart = new Cart();
    cart.setId(UUID.fromString(uuid));
    return cart;
  }

  public static CartProduct generateCartProduct(Long productId, Integer amount) {
    return generateCartProductInternal(productId, amount, BigDecimal.valueOf(100_000));
  }
  public static CartProduct generateCartProduct(Long productId, Integer amount, BigDecimal unitPrice) {
    return generateCartProductInternal(productId, amount, unitPrice);
  }

  private static CartProduct generateCartProductInternal(Long productId, Integer amount, BigDecimal unitPrice) {
    CartProduct cartProduct = new CartProduct();
    cartProduct.setProductId(productId);
    cartProduct.setAmount(amount);
    cartProduct.setProduct(Product.builder()
        .id(productId)
        .price(unitPrice)
        .name("Product 1")
        .build());
    return cartProduct;
  }
}
