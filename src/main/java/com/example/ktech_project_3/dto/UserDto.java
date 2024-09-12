package com.example.ktech_project_3.dto;

import com.example.ktech_project_3.entity.UserEntity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@ToString
@NoArgsConstructor
public class UserDto {
    private Long id;
    @Setter
    private String nickname;

    @Setter
    private String name;
    @Setter
    private String username;
    @Setter
    private Integer age;
    @Setter
    private String email;
    @Setter
    private String phone;
    @Setter
    private String password;
    @Setter
    private String passwordCheck;
    @Setter
    private String profileImgUrl;
    @Setter
    @Enumerated(EnumType.STRING)
    private UserEntity.BusinessStatus businessStatus = UserEntity.BusinessStatus.NONE;

    public static UserDto fromEntity(UserEntity entity) {
        UserDto dto = new UserDto();
        dto.id = entity.getId();
        dto.nickname = entity.getNickname();
        dto.name = entity.getName();
        dto.username = entity.getUsername();
        dto.age = entity.getAge();
        dto.email = entity.getEmail();
        dto.phone = entity.getPhone();
        dto.password = entity.getPassword();
        dto.profileImgUrl = entity.getProfileImgUrl();
        dto.businessStatus = entity.getBusinessStatus();

        return dto;

    }



}
