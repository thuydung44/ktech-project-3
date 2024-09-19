package com.example.ktech_project_3;

import com.example.ktech_project_3.dto.CategoryDto;
import com.example.ktech_project_3.dto.ShopDto;
import com.example.ktech_project_3.entity.Category;
import com.example.ktech_project_3.entity.ShopEntity;
import com.example.ktech_project_3.entity.UserEntity;
import com.example.ktech_project_3.repo.CategoryRepository;
import com.example.ktech_project_3.repo.ShopRepository;
import com.example.ktech_project_3.repo.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShopService {
    private final ShopRepository shopRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public ShopService(
            ShopRepository shopRepository,
            UserService userService,
            UserRepository userRepository,
            CategoryRepository categoryRepository

    ) {
        this.shopRepository = shopRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public ShopEntity createShop() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UserEntity> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent() && userOpt.get().getBusinessStatus() == UserEntity.BusinessStatus.APPROVED) {
            UserEntity user = userOpt.get();
            ShopEntity shop = new ShopEntity();
            shop.setOwner(user);
            shop.setOwnerStatus(ShopEntity.OwnerStatus.PREPARING);
            return shopRepository.save(shop);
        }
        return null;
    }

    public List<ShopEntity> shopList() {
        return shopRepository.findAll();
    }

    public ShopDto updateShop(Long shopId, ShopDto shopDto) {
        Optional<ShopEntity> shopOpt = shopRepository.findById(shopId);
        Category category = categoryRepository.findById(shopDto.getCategory().getId()).orElse(null);
        if (shopOpt.isPresent()) {
            ShopEntity shop = shopOpt.get();
            shop.setName(shopDto.getName());
            shop.setDescription(shopDto.getDescription());
            shop.setCategory(category);
            if (shopDto.getName() == null ||
                    shopDto.getDescription() == null ||
                    shopDto.getCategory() == null) {
                shop.setOwnerStatus(ShopEntity.OwnerStatus.PREPARING);
            } else {
                shop.setOwnerStatus(ShopEntity.OwnerStatus.REQUESTED);
            }

            return ShopDto.fromEntity(shopRepository.save(shop));
        }
        throw new RuntimeException("Shop not found");
    }

    public List<ShopDto> getRequestedShops() {
        List<ShopEntity> shops = shopRepository.findByOwnerStatus(ShopEntity.OwnerStatus.REQUESTED);
        return shops.stream().map(ShopDto::fromEntity).collect(Collectors.toList());
    }

    public ShopDto approveShop(Long shopId) {
        Optional<ShopEntity> shopOpt = shopRepository.findById(shopId);
        if (shopOpt.isPresent()) {
            ShopEntity shop = shopOpt.get();
            if (shop.getOwnerStatus() == ShopEntity.OwnerStatus.REQUESTED) {
                shop.setOwnerStatus(ShopEntity.OwnerStatus.OPENING);
                shopRepository.save(shop);
                return ShopDto.fromEntity(shop);
            }
        }
        throw new RuntimeException("Shop not found or not in requested state");
    }

    public ShopDto rejectShop(Long shopId, String rejectReason) {
        Optional<ShopEntity> shopOpt = shopRepository.findById(shopId);
        if (shopOpt.isPresent() && shopOpt.get().getOwnerStatus() == ShopEntity.OwnerStatus.REQUESTED) {
            ShopEntity shop = shopOpt.get();
            shop.setOwnerStatus(ShopEntity.OwnerStatus.REJECTED);
            shop.setRejectReason(rejectReason);
            shopRepository.save(shop);
            return ShopDto.fromEntity(shop);
        }
        return null;
    }

    public String getRejectReason(Long shopId, String username) {
        Optional<ShopEntity> shopOpt = shopRepository.findById(shopId);
        if (shopOpt.isPresent()) {
            ShopEntity shop = shopOpt.get();
            // Kiểm tra xem người dùng có phải là chủ cửa hàng không
            if (shop.getOwner().getUsername().equals(username)) {
                return shop.getRejectReason();
            } else {
                return "Access denied";
            }
        }
        return null;


    }

    public String closeShopRequest(Long shopId, String closeReason) {
        Optional<ShopEntity> shopOpt = shopRepository.findById(shopId);
        if (shopOpt.isPresent() && shopOpt.get().getOwnerStatus() == ShopEntity.OwnerStatus.OPENING) {
            ShopEntity shop = shopOpt.get();
            shop.setOwnerStatus(ShopEntity.OwnerStatus.CLOSE_REQUESTED);
            shop.setCloseReason(closeReason);
            shopRepository.save(shop);
            return "Shop close requested successfully";
        }
        return "Shop not found or not in opening state";
    }

    public void closeShop(Long shopId) {
        Optional<ShopEntity> shopOpt = shopRepository.findById(shopId);
        if (shopOpt.isPresent() && shopOpt.get().getOwnerStatus() == ShopEntity.OwnerStatus.CLOSE_REQUESTED) {
            ShopEntity shop = shopOpt.get();
            shop.setOwnerStatus(ShopEntity.OwnerStatus.CLOSED);
            shopRepository.save(shop);
        }

    }


    public List<ShopDto> searchShop(String name, Category category) {
        List<ShopEntity> shops;
        if (name!= null && category!= null) {
            shops = shopRepository.findByNameContainingAndCategory(name, category);
        } else if (name!= null) {
            shops = shopRepository.findByNameContaining(name);
        } else if (category!= null) {
            shops = shopRepository.findByCategory(category);
        } else {
            shops = shopRepository.findAll();
        }
        return shops.stream().map(ShopDto::fromEntity).collect(Collectors.toList());

    }
   /* public List<ShopDto> searchShopByName(String name) {
        List<ShopEntity> targetShop = new ArrayList<>();
        for (ShopEntity shop : this.shopList()) {
            if (shop.getName().equals(name)) {
                targetShop.add(shop);
            }
        }
        return

    }*/
}






