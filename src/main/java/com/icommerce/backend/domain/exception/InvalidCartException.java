package com.icommerce.backend.domain.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
public class InvalidCartException extends RuntimeException {
  private final String message;

  public InvalidCartException(final String message) {
    this.message = message;
  }
}
