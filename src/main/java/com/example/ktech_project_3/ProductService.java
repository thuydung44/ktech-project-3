package com.example.ktech_project_3;

import com.example.ktech_project_3.dto.ProductDto;
import com.example.ktech_project_3.dto.ShopDto;
import com.example.ktech_project_3.entity.Product;
import com.example.ktech_project_3.entity.ShopEntity;
import com.example.ktech_project_3.entity.UserEntity;
import com.example.ktech_project_3.repo.ProductRepository;
import com.example.ktech_project_3.repo.ShopRepository;
import com.example.ktech_project_3.repo.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    public final ProductRepository productRepository;
    public final UserRepository userRepository;
    private final ShopRepository shopRepository;
    public ProductService(
            ProductRepository productRepository,
            UserRepository userRepository,
            ShopRepository shopRepository

    ) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.shopRepository = shopRepository;

    }

    public ProductDto createProduct(Long shopId, ProductDto productDto, String username) {
        ShopEntity shop = shopRepository.findById(shopId).orElse(null);
        if (shop.getOwner().getUsername().equals(username)) {
            Product product = new Product();
            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            product.setDescription(productDto.getDescription());
            product.setStock(productDto.getStock());
            product.setShop(shop);

            return ProductDto.fromEntity(productRepository.save(product));

        }
        return null;
    }

    public ProductDto updateProductImg(Long proId, MultipartFile image) {
        Optional<Product> optionalProduct = productRepository.findById(proId);
        if (optionalProduct.isEmpty()) {throw new ResponseStatusException(HttpStatus.NOT_FOUND); }
        String profileDir = "media/" + proId + "/";
        try {
            Files.createDirectories(Path.of(profileDir));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String originalFilename = image.getOriginalFilename();
        String[] filenameSplit = originalFilename.split("\\.");
        String extension = filenameSplit[filenameSplit.length - 1];
        String uploadPath = profileDir + "product" + extension;
        try {
            image.transferTo(Path.of(uploadPath));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String reqPath = "/media/" + proId + "/product" + extension;
        Product target = optionalProduct.get();
        target.setProductImgUrl(reqPath);
        return ProductDto.fromEntity(productRepository.save(target));

    }

    public ProductDto updateProduct(Long proId, ProductDto productDto) {
        Optional<Product> optionalProduct = productRepository.findById(proId);
        if (optionalProduct.isEmpty()) {throw new ResponseStatusException(HttpStatus.NOT_FOUND); }
        Product target = optionalProduct.get();
        target.setName(productDto.getName());
        target.setPrice(productDto.getPrice());
        target.setDescription(productDto.getDescription());
        target.setStock(productDto.getStock());
//        target.setProductImgUrl(productDto.getProductImgUrl());
        return ProductDto.fromEntity(productRepository.save(target));

    }

    public void deleteProduct(Long proId) {
        Optional<Product> optionalProduct = productRepository.findById(proId);
        if (optionalProduct.isEmpty()) {throw new ResponseStatusException(HttpStatus.NOT_FOUND); }
        productRepository.deleteById(proId);
    }

    // 이름 또는 가격으로 검색
    public List<ProductDto> searchProduct(String name, Double minPrice, Double maxPrice) {
        List<Product> products;
        if (name!= null && minPrice != 0.0 && maxPrice != 0.0) {
            products = productRepository.findAllByNameAndPriceBetween(name, minPrice, maxPrice);

        } else if (name != null) {
            products = productRepository.findByNameContaining(name);
        } else if (minPrice == 0 && maxPrice!= 0.0) {
            products = productRepository.findByPriceBetween(minPrice, maxPrice);
        } else {
            products = productRepository.findAll();
        }
        return products.stream().map(ProductDto::fromEntity).collect(Collectors.toList());

    }



    }





