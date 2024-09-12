package com.example.ktech_project_3.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity owner;

    @Setter
    private String description;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category")
    private Category category;
    @Setter
    private String rejectReason;
    @Setter
    private String closeReason;
    @Setter
    @OneToMany(mappedBy = "shop")
    private List<Product> products;

    @Setter
    @Enumerated(EnumType.STRING)
    private OwnerStatus ownerStatus = OwnerStatus.PREPARING;
    public enum OwnerStatus {
        PREPARING,
        REQUESTED,
        OPENING,
        REJECTED,
        CLOSE_REQUESTED,
        CLOSED
    }



}
