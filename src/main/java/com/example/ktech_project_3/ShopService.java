package com.example.ktech_project_3;

import com.example.ktech_project_3.dto.ShopDto;
import com.example.ktech_project_3.entity.Category;
import com.example.ktech_project_3.entity.ShopEntity;
import com.example.ktech_project_3.entity.UserEntity;
import com.example.ktech_project_3.repo.CategoryRepository;
import com.example.ktech_project_3.repo.ShopRepository;
import com.example.ktech_project_3.repo.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public ShopDto updateShop(Long shopId, ShopDto shopDto) {
        Optional<ShopEntity> shopOpt = shopRepository.findById(shopId);
        Category category = shopDto.getCategory();
        if (shopOpt.isPresent()) {
            ShopEntity shop = shopOpt.get();
            shop.setName(shopDto.getName());
            shop.setDescription(shopDto.getDescription());
            shop.setCategory(category);
            if (shopDto.getName() == null ||
                    shopDto.getDescription() == null ||
                    shop.getCategory() == null) {
                shop.setOwnerStatus(ShopEntity.OwnerStatus.PREPARING);
            } else {
                shop.setOwnerStatus(ShopEntity.OwnerStatus.REQUESTED);
            }

            return ShopDto.fromEntity(shopRepository.save(shop));
        }
        throw new RuntimeException("Shop not found");
    }

}
