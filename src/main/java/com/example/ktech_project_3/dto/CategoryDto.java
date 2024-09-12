package com.example.ktech_project_3.dto;

import com.example.ktech_project_3.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor

public class CategoryDto {
    private Long id;
    @Setter
    private String name;
    public static CategoryDto fromEntity(Category entity) {
        CategoryDto dto = new CategoryDto();
        dto.id = entity.getId();
        dto.name = entity.getName();
        return dto;
    }
}
