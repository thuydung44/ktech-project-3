package com.example.ktech_project_3.dto;

// import com.example.ktech_project_3.entity.Order;
import com.example.ktech_project_3.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class ProductDto {
    private Long id;
    @Setter
    private String name;
    @Setter
    private String productImgUrl;
    @Setter
    private String description;
    @Setter
    private Double price;
    @Setter
    private Integer stock;
    @Setter
    private ShopDto shop;

    public static ProductDto fromEntity(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.id = product.getId();
        productDto.name = product.getName();
        productDto.productImgUrl = product.getProductImgUrl();
        productDto.description = product.getDescription();
        productDto.price = product.getPrice();
        productDto.stock = product.getStock();
        productDto.shop = ShopDto.fromEntity(product.getShop());

        return productDto;
    }


}
