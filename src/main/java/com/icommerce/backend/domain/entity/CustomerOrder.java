package com.icommerce.backend.domain.entity;

import com.icommerce.backend.domain.type.OrderStatus;
import com.icommerce.backend.domain.type.PaymentMethod;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "customer_order")
@EntityListeners({ AuditingEntityListener.class })
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOrder {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(name = "cart_id")
  private UUID cartId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cart_id", insertable = false, updatable = false)
  private Cart cart;

  private String customerName;

  private String address;

  private String phoneNumber;

  private BigDecimal amount;

  private PaymentMethod paymentMethod;

  private OrderStatus status;

  @CreatedDate
  private ZonedDateTime createdDate;
}
