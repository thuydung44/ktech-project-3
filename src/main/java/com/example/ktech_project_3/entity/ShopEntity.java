package com.example.ktech_project_3.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Table(name = "shop_table")
@NoArgsConstructor
@AllArgsConstructor
public class ShopEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Setter
    private String name;
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner")
    private UserEntity owner;

    @Setter
    private String description;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category")
    private Category category;


    @Setter
    @Enumerated(EnumType.STRING)
    private OwnerStatus ownerStatus = OwnerStatus.PREPARING;
    public enum OwnerStatus {
        PREPARING,
        REQUESTED,
        OPENING,
        CLOSED
    }



}
