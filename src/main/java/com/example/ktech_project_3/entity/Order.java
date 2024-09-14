package com.example.ktech_project_3.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "orders_table")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @ManyToOne
    private Product product;
    @Setter
    @ManyToOne
    private UserEntity buyer;
    @Setter
    private Integer quantity;

    @Setter
    private Double totalAmount;
    @Setter
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public enum OrderStatus {
        PENDING,
        ACCEPTED,
        CANCELED,
    }

    @Setter
    private boolean isPaid = false;


}
