package com.icommerce.backend.presentation.utils;

import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

@NoArgsConstructor(access = AccessLevel.NONE)
public class ErrorUtils {
  public static Map<String, Object> createError(HttpStatus status, String message) {
    Assert.notNull(status, "status must not be null");
    return Map.of(
        "status", status.value(),
        "error", message
    );
  }
}
