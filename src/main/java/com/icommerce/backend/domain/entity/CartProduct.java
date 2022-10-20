package com.icommerce.backend.domain.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cart_product")
@Getter
@Setter
public class CartProduct {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq-cart-product-id")
  @SequenceGenerator(name = "seq-cart-product-id", sequenceName = "seq_cart_product_id", allocationSize = 1)
  private Long id;

  @Column(name = "product_id")
  private Long productId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", insertable = false, updatable = false)
  private Product product;

  private Integer amount;

  private UUID cartId;
}
