package com.example.ktech_project_3.dto;

import com.example.ktech_project_3.entity.Category;
import com.example.ktech_project_3.entity.ShopEntity;
import com.example.ktech_project_3.entity.UserEntity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class ShopDto {
    private Long id;
    @Setter
    private String name;
    @Setter
    private  String description;
    @Setter
    private Category category;

    @Setter
    private UserEntity owner;
    @Enumerated(EnumType.STRING)
    private ShopEntity.OwnerStatus ownerStatus = ShopEntity.OwnerStatus.PREPARING;

    public static ShopDto fromEntity(ShopEntity shop) {
        ShopDto shopDto = new ShopDto();
        shopDto.id = shop.getId();
        shopDto.name = shop.getName();
        shopDto.description = shop.getDescription();
        shopDto.category = shop.getCategory();
        shopDto.owner = shop.getOwner();
        shopDto.ownerStatus = shop.getOwnerStatus();
        return shopDto;
    }
}
