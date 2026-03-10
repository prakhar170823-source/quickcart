package com.quickcart.repository;

import com.quickcart.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByCategoryAndPriceBetween(String category, BigDecimal minPrice, BigDecimal maxPrice);

    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
}
