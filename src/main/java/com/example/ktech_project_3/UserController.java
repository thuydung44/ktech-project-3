package com.example.ktech_project_3;

import com.example.ktech_project_3.dto.UserDto;
import com.example.ktech_project_3.entity.UserEntity;
import com.example.ktech_project_3.jwt.dto.JwtRequestDto;
import com.example.ktech_project_3.jwt.dto.JwtResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public UserController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;

    }

    @GetMapping("login")
    public String loginForm() {
        return "login-form";
    }

    @GetMapping("my-profile")
    public String myProfile() {
        return "my-profile";
    }

    @GetMapping("register")
    public String signUpForm() {
        return "register-form";
    }

    @PostMapping("register")
    public String signUpRequest(
            @RequestBody
            UserDto dto
    ) {
        if (dto.getPassword().equals(dto.getPasswordCheck())) {
            userService.createUser(dto);
        }
        return dto.getUsername();
    }

    @PutMapping("/update")
    public UserDto updateUser(
            @RequestBody
            UserDto dto
    ) {
        Long updateId = userService.readByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
        ;
        return userService.updateUser(updateId, dto);
    }

    @PutMapping("/update/image")
    public UserDto updateUserImage(
            @RequestParam("image")
            MultipartFile image
    ) {
        Long updateId = userService.readByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
        return userService.updateProfileImg(updateId, image);
    }


    @PostMapping("/apply-business")
    public ResponseEntity<String> applyBusiness(
    ) {
        UserEntity user = userService.applyForBusiness(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user != null) {
            return ResponseEntity.ok("You have applied for business with " + user.getUsername());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to apply for business");
        }
    }

    // 목록 조회
    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/view")
    public ResponseEntity<List<UserEntity>> getBusinessApplications() {
        List<UserEntity> applications = userService.getBusinessApplyList();
        if (applications.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } else {
            return ResponseEntity.ok(applications);
        }
    }

    // 수락
    @Secured("ROLE_ADMIN")
    @PostMapping("/admin/{userId}/accept")
    public ResponseEntity<UserEntity> acceptBusinessApplication(
            @PathVariable Long userId) {
        UserEntity user = userService.acceptBusinessApply(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/admin/{userId}/reject")
    public ResponseEntity<UserEntity> rejectBusinessApplication(
            @PathVariable Long userId) {
        UserEntity user = userService.rejectBusinessApply(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}