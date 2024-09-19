package com.example.ktech_project_3.dto;

import com.example.ktech_project_3.entity.Order;
import com.example.ktech_project_3.entity.Product;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class OrderDto {
    private Long id;
   // @Setter
    private ProductDto product;
   // @Setter
    private UserDto buyer;
   // @Setter
    private Integer quantity;
   // @Setter
    private Double totalAmount;
   // @Setter
   // @Enumerated(EnumType.STRING)
    private Order.OrderStatus orderStatus = Order.OrderStatus.PENDING;
    private boolean idPaid;
    public static OrderDto fromEntity(Order entity) {
        OrderDto dto = new OrderDto();
        dto.id = entity.getId();
        dto.product = ProductDto.fromEntity(entity.getProduct());
        dto.buyer = UserDto.fromEntity(entity.getBuyer());
        dto.quantity = entity.getQuantity();
        dto.totalAmount = entity.getTotalAmount();
        dto.orderStatus = entity.getOrderStatus();
        dto.idPaid = entity.isPaid();
        return dto;
    }


}
