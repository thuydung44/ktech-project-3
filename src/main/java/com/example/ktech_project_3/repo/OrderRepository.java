package com.example.ktech_project_3.repo;

import com.example.ktech_project_3.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
