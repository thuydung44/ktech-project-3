package com.example.ktech_project_3;

import com.example.ktech_project_3.dto.ShopDto;
import com.example.ktech_project_3.entity.ShopEntity;
import com.example.ktech_project_3.entity.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/shops")
public class ShopController {
    private final ShopService shopService;
    private final UserService userService;
    public ShopController(ShopService shopService, UserService userService) {
        this.shopService = shopService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<ShopEntity> createShop() {
        ShopEntity shop = shopService.createShop();
        if (shop != null) {
            return ResponseEntity.ok(shop);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    @PutMapping("/{shopId}/update")
    public ResponseEntity<ShopDto> updateShop(
            @PathVariable Long shopId,
            @RequestBody ShopDto shopDto) {
        try {
            // 쇼핑몰 업데이트 처리
            ShopDto updatedShop = shopService.updateShop(shopId, shopDto);

            // 성공 시, 업데이트된 쇼핑몰 정보 반환
            return ResponseEntity.ok(updatedShop);
        } catch (RuntimeException e) {
            // 예외 발생 시, BAD_REQUEST 상태와 함께 에러 메시지 반환
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}




