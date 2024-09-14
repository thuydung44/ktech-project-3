package com.example.ktech_project_3.repo;

import com.example.ktech_project_3.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);
    // 이름으로 검색
    List<Product> findAllByNameAndPriceBetween(String name, Double minPrice, double maxPrice);
    List<Product> findByNameContaining(String name);
    // 가격으로 검색
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);
}
