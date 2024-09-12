package com.example.ktech_project_3.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Setter
    private String name;

    public Category() {};

    public Category(String name) {
        this.name = name;
    }
}
