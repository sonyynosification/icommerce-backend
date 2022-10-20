package com.icommerce.backend.service;

import static com.icommerce.backend.DataUtils.generateCart;
import static com.icommerce.backend.DataUtils.generateCartProduct;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.icommerce.backend.domain.entity.Cart;
import com.icommerce.backend.domain.entity.CartProduct;
import com.icommerce.backend.domain.exception.InvalidCartException;
import com.icommerce.backend.domain.exception.InvalidProductException;
import com.icommerce.backend.domain.repository.CartProductRepository;
import com.icommerce.backend.domain.repository.CartRepository;
import com.icommerce.backend.domain.repository.ProductRepository;
import com.icommerce.backend.presentation.request.AddToCartRequest;
import com.icommerce.backend.presentation.request.UpdateCartRequest;
import com.icommerce.backend.presentation.response.CartResponse;
import com.icommerce.backend.service.mapper.CartMapper;
import com.icommerce.backend.service.mapper.CartProductMapper;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {
  @Mock
  private CartRepository cartRepository;
  @Mock
  private ProductRepository productRepository;
  @Mock
  private CartProductRepository cartProductRepository;
  @Spy
  private CartMapper cartMapper = Mappers.getMapper(CartMapper.class);
  @Spy
  private CartProductMapper cartProductMapper = Mappers.getMapper(CartProductMapper.class);
  @Mock
  private CostService costService;

  @InjectMocks
  private CartServiceImpl cartService;

  @Captor
  private ArgumentCaptor<CartProduct> cartProductCaptor;

  @Captor
  private ArgumentCaptor<Cart> cartCaptor;

  @Captor
  private ArgumentCaptor<Iterable<CartProduct>> cartProductsCaptor;

  private static final String CART_ID = "b228a48b-9caa-49ef-a1ed-ba866e239a12";

  @Test
  void addToCart_whenCartIdNull() {
    doReturn(generateCart(CART_ID))
        .when(cartRepository).save(any(Cart.class));
    doReturn(true).when(productRepository)
        .existsById(1L);

    AddToCartRequest request = new AddToCartRequest();
    request.setProductId(1L);
    request.setAmount(10);
    final CartResponse cartResponse = cartService.addToCart(request);
    assertEquals(CART_ID, cartResponse.getCartId());
  }

  @Test
  void addToCart_whenInvalidCartId() {
    doReturn(Optional.empty()).when(cartRepository)
        .findById(any(UUID.class));

    AddToCartRequest request = new AddToCartRequest();
    request.setCartId(CART_ID);
    assertThrows(InvalidCartException.class, () -> cartService.addToCart(request));
  }

  @Test
  void addToCart_whenProductIdInvalid() {
    doReturn(Optional.of(generateCart(CART_ID)))
        .when(cartRepository).findById(any(UUID.class));
    doReturn(false).when(productRepository)
        .existsById(1L);

    AddToCartRequest request = new AddToCartRequest();
    request.setCartId(CART_ID);
    request.setProductId(1L);
    assertThrows(InvalidProductException.class, () -> cartService.addToCart(request));
  }

  @Test
  void addToCart_success() {
    doReturn(Optional.of(generateCart(CART_ID)))
        .when(cartRepository).findById(any(UUID.class));
    doReturn(true).when(productRepository)
        .existsById(1L);

    AddToCartRequest request = new AddToCartRequest();
    request.setCartId(CART_ID);
    request.setProductId(1L);
    request.setAmount(10);


    final CartResponse cartResponse = cartService.addToCart(request);
    assertEquals(CART_ID, cartResponse.getCartId());
    assertEquals(1, cartResponse.getProducts().size());
    verify(cartProductRepository, times(1)).save(cartProductCaptor.capture());
    var cartProduct = cartProductCaptor.getValue();
    assertEquals(1, cartProduct.getProductId());
    assertEquals(CART_ID, cartProduct.getCartId().toString());
    assertEquals(10, cartProduct.getAmount());
  }

  @Test
  void getDetails_whenEmpty() {
    doReturn(Optional.empty())
        .when(cartRepository).findById(UUID.fromString(CART_ID));

    assertThrows(InvalidCartException.class, () -> cartService.getDetails(CART_ID));
  }

  @Test
  void getDetails_whenHavingValues() {
    doReturn(Optional.of(generateCart(CART_ID)))
        .when(cartRepository).findById(UUID.fromString(CART_ID));
    doReturn(List.of(
        generateCartProduct(1L, 10)
    )).when(cartProductRepository).findByCartId(UUID.fromString(CART_ID));

    final CartResponse details = cartService.getDetails(CART_ID);
    assertEquals(1, details.getProducts().size());
    assertEquals(1, details.getProducts().get(0).getProduct().getId());
    assertEquals("Product 1", details.getProducts().get(0).getProduct().getName());
  }

  @Test
  void updateCart_whenCartIdInvalid() {
    doReturn(Optional.empty())
        .when(cartRepository).findById(UUID.fromString(CART_ID));

    UpdateCartRequest request = new UpdateCartRequest();

    assertThrows(InvalidCartException.class, () -> cartService.updateCart(CART_ID, request));
  }

  @Test
  void updateCart_whenHavingDeletedItems() {
    doReturn(Optional.of(generateCart(CART_ID)))
        .when(cartRepository).findById(UUID.fromString(CART_ID));
    doReturn(List.of(
        generateCartProduct(1L, 10)
    )).when(cartProductRepository).findByCartId(UUID.fromString(CART_ID));

    var request = new UpdateCartRequest();
    request.setItems(Collections.emptyList());
    cartService.updateCart(CART_ID, request);

    verify(cartProductRepository, times(1)).deleteAllById(anyCollection());
  }

  @Test
  void updateCart_whenUpdateAmount() {
    doReturn(Optional.of(generateCart(CART_ID)))
        .when(cartRepository).findById(UUID.fromString(CART_ID));
    doReturn(List.of(
        generateCartProduct(1L, 10)
    )).when(cartProductRepository).findByCartId(UUID.fromString(CART_ID));


    var request = new UpdateCartRequest();
    var updateItem = new UpdateCartRequest.Item();
    updateItem.setProductId(1L);
    updateItem.setAmount(20);
    request.setItems(List.of(
        updateItem
    ));
    cartService.updateCart(CART_ID, request);

    verify(cartProductRepository, times(1)).saveAll(cartProductsCaptor.capture());
    var savedCartProducts = StreamSupport.stream(cartProductsCaptor.getValue().spliterator(), false)
        .collect(Collectors.toList())
        ;
    assertEquals(1, savedCartProducts.size());
    assertEquals(20, savedCartProducts.get(0).getAmount());

  }


}