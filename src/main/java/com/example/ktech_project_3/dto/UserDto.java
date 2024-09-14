package com.example.ktech_project_3.dto;

import com.example.ktech_project_3.entity.Order;
import com.example.ktech_project_3.entity.UserEntity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String nickname;
    private String name;
    private String username;
    private Integer age;
    private String email;
    private String phone;
    private String password;
    private String passwordCheck;
    private String profileImgUrl;
    @Enumerated(EnumType.STRING)
    private UserEntity.BusinessStatus businessStatus = UserEntity.BusinessStatus.NONE;


    public static UserDto fromEntity(UserEntity entity) {
        UserDto dto = new UserDto();
        dto.id = entity.getId();
        dto.nickname = entity.getNickname();
        dto.name = entity.getName();
        dto.username = entity.getUsername();
        dto.email = entity.getEmail();
        dto.phone = entity.getPhone();
        dto.password = entity.getPassword();
        dto.profileImgUrl = entity.getProfileImgUrl();
        dto.businessStatus = entity.getBusinessStatus();


        return dto;

    }



}
