package com.icommerce.backend.presentation.request;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.CollectionUtils;

public interface SearchRequest {

  default int getPage() {
    return 0;
  }

  default int getSize() {
    return 5;
  }

  default List<String> getSort() {
    return Collections.emptyList();
  }

  default Pageable getPageable() {
    final Sort sort;
    if (CollectionUtils.isEmpty(getSort())) {
      sort = Sort.unsorted();
    } else {
      var orders  = getSort()
          .stream()
          .map(s -> {
            var ar = s.split(":");
            return new Sort.Order(ar[1].equalsIgnoreCase("ASC")? Direction.ASC : Direction.DESC,ar[0]);
          })
          .collect(Collectors.toList());

      sort = Sort.by(orders);
    }

    return PageRequest.of(getPage(), getSize(), sort);
  }
}