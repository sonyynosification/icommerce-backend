package com.icommerce.backend.service;

import com.icommerce.backend.presentation.request.SearchProductRequest;
import com.icommerce.backend.presentation.response.ProductResponse;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;

public interface ProductService {

  Page<ProductResponse> search(@NonNull SearchProductRequest request);

  Optional<ProductResponse> findById(@NonNull Long id);
}
