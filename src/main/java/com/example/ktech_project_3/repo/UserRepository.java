package com.example.ktech_project_3.repo;

import com.example.ktech_project_3.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
    List<UserEntity> findByBusinessStatus(UserEntity.BusinessStatus status);
}
