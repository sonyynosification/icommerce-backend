package com.icommerce.backend.domain.entity;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product {
  @Id
  private Long id;

  private String name;

  private String sku;

  private String color;

  private BigDecimal price;

  @Column(name = "category_id")
  private Integer categoryId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", insertable = false, updatable = false)
  private Category category;

  @Column(name = "brand_id")
  private Integer brandId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "brand_id", insertable = false, updatable = false)
  private Brand brand;

  @CreatedDate
  private ZonedDateTime createdDate;

  @LastModifiedDate
  private ZonedDateTime modifiedDate;
}
