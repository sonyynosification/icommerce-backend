package com.icommerce.backend.domain.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
public class InvalidProductException extends RuntimeException {
  private final String message;

  public InvalidProductException(final String message) {
    this.message = message;
  }
}
