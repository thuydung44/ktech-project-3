package com.example.ktech_project_3.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "user_table")
@NoArgsConstructor(force = true)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String authorities;
    // 사업자 전환 신청 상태: NONE, PENDING, APPROVED, REJECTED
    @Setter
    @Enumerated(EnumType.STRING)
    private BusinessStatus businessStatus = BusinessStatus.NONE;
    public enum BusinessStatus {
        NONE,       // 신청하지 않음
        PENDING,    // 신청 대기 중
        APPROVED,   // 신청 승인됨
        REJECTED    // 신청 거절됨
    }



}
