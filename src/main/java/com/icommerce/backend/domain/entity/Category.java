package com.icommerce.backend.domain.entity;

import java.time.ZonedDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "category")
@Getter
@Setter
public class Category {
  @Id
  private Integer id;

  private String name;

}
