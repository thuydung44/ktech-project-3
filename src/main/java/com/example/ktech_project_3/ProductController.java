package com.example.ktech_project_3;

import com.example.ktech_project_3.dto.ProductDto;
import com.example.ktech_project_3.dto.ShopDto;
import com.example.ktech_project_3.entity.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping("/{shopId}/create")
    public ResponseEntity<ProductDto> createProduct(
            @PathVariable Long shopId,
            @RequestBody ProductDto productDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        ProductDto product = productService.createProduct(shopId, productDto, username);
        return ResponseEntity.ok(product);
    }
    @PutMapping("/update/{proId}/image")
    public ProductDto updateProduct(
            @PathVariable Long proId,
            @RequestParam("image")
            MultipartFile image
    ) {
        return productService.updateProductImg(proId, image);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable Long productId,
            @RequestBody ProductDto productDto
    ) {
        ProductDto product = productService.updateProduct(productId, productDto);

        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/delete/{productId}")
    public String deleteProduct(
            @PathVariable Long productId
    ) {
        productService.deleteProduct(productId);
        return "Detele successly!!";
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchProducts(
            @RequestParam String name,
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice
    ) {
        return ResponseEntity.ok(productService.searchProduct(name, minPrice, maxPrice));
    }
    }



