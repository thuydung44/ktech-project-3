package com.example.ktech_project_3;

import com.example.ktech_project_3.dto.RejectReason;
import com.example.ktech_project_3.dto.ShopCloseRequest;
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
import org.springframework.ui.Model;
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

        ShopDto updatedShop = shopService.updateShop(shopId, shopDto);
        return ResponseEntity.ok(updatedShop);

    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/view")
    public ResponseEntity<List<ShopDto>> getAdminShops() {
        List<ShopDto> shopApplications = shopService.getRequestedShops();

        return ResponseEntity.ok(shopApplications);

    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/admin/{shopId}/accept")
    public ResponseEntity<ShopDto> acceptShopApplication(
            @PathVariable Long shopId) {
        ShopDto shop = shopService.approveShop(shopId);
        return ResponseEntity.ok(shop);

    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/admin/{shopId}/reject")
    public ResponseEntity<ShopDto> rejectShopApplication(
            @PathVariable
            Long shopId,
            @RequestBody RejectReason rejectReason
    ) {
        ShopDto shop = shopService.rejectShop(shopId, rejectReason.getRejectReason());
        return ResponseEntity.ok(shop);

    }

    @GetMapping("/{shopId}/reject-reason")
    public String rejectReason
            (@PathVariable Long shopId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return shopService.getRejectReason(shopId, username);
    }

    @PostMapping("/{shopId}/close-reason")
    public ResponseEntity<String> closeReason(
            @PathVariable Long shopId,
            @RequestBody ShopCloseRequest shopCloseRequest) {
        String shop = shopService.closeShopRequest(shopId, shopCloseRequest.getCloseReason());
        return ResponseEntity.ok(shop);




    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/{shopId}/close")
    public String closeShop(
            @PathVariable Long shopId) {
        shopService.closeShop(shopId);
        return "Shop closed successfully";

    }
}




