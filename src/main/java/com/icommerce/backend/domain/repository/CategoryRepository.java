package com.icommerce.backend.domain.repository;

import com.icommerce.backend.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
