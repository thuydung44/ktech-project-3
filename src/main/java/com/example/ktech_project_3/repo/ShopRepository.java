package com.example.ktech_project_3.repo;

import com.example.ktech_project_3.entity.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShopRepository extends JpaRepository<ShopEntity, Long>{
    Optional<ShopEntity> findByName(String name);
    boolean existsByName(String name);
    List<ShopEntity> findByOwnerStatus(ShopEntity.OwnerStatus status);

}
