package com.icommerce.backend.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "brand")
@Getter
@Setter
public class Brand {
  @Id
  private Integer id;

  private String name;

}
