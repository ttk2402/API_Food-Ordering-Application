package com.kt.backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kt.backend.entity.Category;
import com.kt.backend.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
	
	List<Product> findByCategory(Category category);
	
}
