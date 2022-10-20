package com.icommerce.backend.presentation.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ErrorUtilsTest {

  @Test
  void createError_whenStatusIsNull() {
    assertThrows(IllegalArgumentException.class, () -> ErrorUtils.createError(null, "message"));
  }


}