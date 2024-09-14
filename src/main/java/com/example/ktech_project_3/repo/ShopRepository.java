package com.example.ktech_project_3.repo;

import com.example.ktech_project_3.entity.Category;
import com.example.ktech_project_3.entity.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ShopRepository extends JpaRepository<ShopEntity, Long>{
    Optional<ShopEntity> findByName(String name);
    boolean existsByName(String name);
    List<ShopEntity> findByOwnerStatus(ShopEntity.OwnerStatus status);
    // 가장 최근에 거래가 있었던 쇼핑몰 순서로 조회된다
    /*@Query("SELECT s FROM ShopEntity s WHERE s.")
    List<ShopEntity> findAllByOrderByLastShoppingTimeDesc();*/
    List<ShopEntity> findByNameContainingAndCategory(String name, Category category);


    // 이름로 조회
    List<ShopEntity> findByNameContaining(String name);

    // Category로 조회
    List<ShopEntity> findByCategory(Category category);



}
