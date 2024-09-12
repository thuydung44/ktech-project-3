package com.example.ktech_project_3.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Setter
    private String name;
    @Setter
    private String productImgUrl;
    @Setter
    private String description;
    @Setter
    private double price;
    @Setter
    private Integer stock;
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private ShopEntity shop;

}