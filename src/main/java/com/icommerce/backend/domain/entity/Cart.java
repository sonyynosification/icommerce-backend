package com.icommerce.backend.domain.entity;

import com.icommerce.backend.domain.type.CartStatus;
import com.icommerce.backend.domain.type.PaymentMethod;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cart")
@Getter
@Setter
public class Cart {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  private CartStatus status;


}
