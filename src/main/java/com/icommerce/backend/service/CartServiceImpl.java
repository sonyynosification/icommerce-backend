package com.icommerce.backend.service;

import com.icommerce.backend.domain.exception.InvalidCartException;
import com.icommerce.backend.domain.exception.InvalidProductException;
import com.icommerce.backend.domain.type.CartStatus;
import com.icommerce.backend.domain.entity.Cart;
import com.icommerce.backend.domain.entity.CartProduct;
import com.icommerce.backend.domain.repository.CartProductRepository;
import com.icommerce.backend.domain.repository.CartRepository;
import com.icommerce.backend.domain.repository.ProductRepository;
import com.icommerce.backend.presentation.request.AddToCartRequest;
import com.icommerce.backend.presentation.request.CheckoutRequest;
import com.icommerce.backend.presentation.request.UpdateCartRequest;
import com.icommerce.backend.presentation.request.UpdateCartRequest.Item;
import com.icommerce.backend.presentation.response.CartResponse;
import com.icommerce.backend.service.mapper.CartMapper;
import com.icommerce.backend.service.mapper.CartProductMapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class CartServiceImpl implements CartService {

  private final CostService costService;
  private final ProductRepository productRepository;
  private final CartProductRepository cartProductRepository;
  private final CartRepository cartRepository;
  private final CartProductMapper cartProductMapper;
  private final CartMapper cartMapper;

  public CartServiceImpl(CostService costService,
      ProductRepository productRepository,
      CartProductRepository cartProductRepository,
      CartRepository cartRepository,
      CartProductMapper cartProductMapper,
      CartMapper cartMapper) {
    this.costService = costService;
    this.productRepository = productRepository;
    this.cartProductRepository = cartProductRepository;
    this.cartRepository = cartRepository;
    this.cartProductMapper = cartProductMapper;
    this.cartMapper = cartMapper;
  }

  @Override
  @Transactional
  public CartResponse addToCart(AddToCartRequest request) {
    final Cart cart = findOrCreate(request.getCartId());

    // find existing product
    if (!productRepository.existsById(request.getProductId())) {
        throw createInvalidProductException(request.getProductId()).get();
    }

    // find existing products in cart
    final List<CartProduct> existingCartProducts = request.getCartId() == null ?
        new ArrayList<>() : findProductsInCart(cart.getId());

    // add new amount to existing product in cart, or create a new one
    final CartProduct addedProduct = existingCartProducts.stream()
        .filter(cartProduct -> cartProduct.getProductId().equals(request.getProductId()))
        .findFirst()
        .map(existingProduct -> {
          existingProduct.setAmount(request.getAmount() + existingProduct.getAmount());
          return existingProduct;
        })
        .orElseGet(() -> buildProductInCart(request, cart));

    // save cart product
    cartProductRepository.save(addedProduct);

    final var cartProducts = new ArrayList<>(existingCartProducts);
    cartProducts.add(addedProduct);

    return createCartResponse(cart, cartProducts);
  }

  private List<CartProduct> findProductsInCart(UUID cartId) {
    return cartProductRepository.findByCartId(cartId);
  }

  private Cart findOrCreate(String cartId) {
    final Cart cart;
    if (!StringUtils.hasText(cartId)) {
      // create a new cart
      cart = createNew();
    } else {
      cart = findCart(cartId)
          .orElseThrow(createInvalidCartException(cartId));
    }
    return cart;
  }

  private CartProduct buildProductInCart(AddToCartRequest request, Cart cart) {
    final CartProduct addedProduct;
    addedProduct = new CartProduct();
    addedProduct.setProductId(request.getProductId());
    addedProduct.setAmount(request.getAmount());
    addedProduct.setCartId(cart.getId());
    return addedProduct;
  }

  private CartResponse createCartResponse(Cart cart, List<CartProduct> cartProductList) {
    final CartResponse cartResponse = cartMapper.toResponse(cart);

    final var products = cartProductList.stream()
        .map(cartProductMapper::toResponse)
        .collect(Collectors.toList());
    cartResponse.setProducts(products);
    cartResponse.setCostSummary(costService.findCost(cartProductList));

    return cartResponse;
  }

  private Cart createNew() {
    final Cart cart = new Cart();
    cart.setStatus(CartStatus.NEW);
    return cartRepository.save(cart);
  }

  private Optional<Cart> findCart(String cartId) {
    return cartRepository.findById(UUID.fromString(cartId));
  }

  @Override
  @Transactional
  public CartResponse updateCart(String cartId, UpdateCartRequest request) {
    final Cart cart = findCart(cartId)
        .orElseThrow(createInvalidCartException(cartId));

    final var updatedItemsByProductId = request.getItems()
        .stream()
        .collect(Collectors.toMap(
            Item::getProductId,
            Function.identity(),
            (e1, e2) -> e2
        ));

    final var partitionedProductsByExistence = findProductsInCart(cart.getId())
        .stream()
        .collect(Collectors.partitioningBy(cartProduct -> cartProduct.getAmount() == 0 || updatedItemsByProductId.containsKey(cartProduct.getProductId())));

    // remove from carts
    final var removedProducts = partitionedProductsByExistence.get(Boolean.FALSE);
    deleteCartProducts(removedProducts);

    // update remaining
    final var updatedProducts = partitionedProductsByExistence.get(Boolean.TRUE);
    updatedProducts.forEach(product -> {
      final var item = updatedItemsByProductId.get(product.getProductId());
      product.setAmount(item.getAmount());
    });
    cartProductRepository.saveAll(updatedProducts);

    return createCartResponse(cart, updatedProducts);
  }

  private void deleteCartProducts(List<CartProduct> cartProductList) {
    cartProductRepository.deleteAllById(
        cartProductList.stream()
            .map(CartProduct::getId)
            .collect(Collectors.toSet())
    );
  }

  @Override
  @Transactional
  public void checkout(String cartId, CheckoutRequest request) {
    final var cart = findCart(cartId)
        .filter(c -> c.getStatus() == CartStatus.NEW)
        .orElseThrow(createInvalidCartException(cartId));

    cartMapper.updateDetails(request, cart);

    cart.setStatus(CartStatus.PROCESSING);

    cartRepository.save(cart);
  }

  @Override
  @Transactional(readOnly = true)
  public CartResponse getDetails(String cartId) {
    final var cart = findCart(cartId)
        .orElseThrow(createInvalidCartException(cartId));
    final var products = findProductsInCart(cart.getId());

    return createCartResponse(cart, products);
  }

  private Supplier<InvalidCartException> createInvalidCartException(final String cartId) {
    return () -> new InvalidCartException("Invalid cart: " + cartId);
  }

  private Supplier<InvalidProductException> createInvalidProductException(final Long productId) {
    return () -> new InvalidProductException("Invalid product: " + productId);
  }
}
