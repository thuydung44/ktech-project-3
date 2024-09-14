

package com.example.ktech_project_3;

import com.example.ktech_project_3.dto.OrderDto;
import com.example.ktech_project_3.dto.ShopDto;
import com.example.ktech_project_3.dto.UserDto;
import com.example.ktech_project_3.entity.Order;
import com.example.ktech_project_3.entity.Product;
import com.example.ktech_project_3.entity.ShopEntity;
import com.example.ktech_project_3.entity.UserEntity;
import com.example.ktech_project_3.repo.OrderRepository;
import com.example.ktech_project_3.repo.ProductRepository;
import com.example.ktech_project_3.repo.ShopRepository;
import com.example.ktech_project_3.repo.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;

    public OrderService(
            OrderRepository orderRepository,
            UserRepository userRepository,
            ProductRepository productRepository,
            ShopRepository shopRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.shopRepository = shopRepository;
    }

    // 구매 생성
        public OrderDto createOrder(OrderDto orderDto, String name) {
        Product product = productRepository.findById(orderDto.getProduct().getId()).orElseThrow();
        UserEntity buyer = userRepository.findByUsername(name).orElse(null);
        if (product.getStock() < orderDto.getQuantity()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough stock");
        }
            Order order = new Order();
            order.setProduct(product);
            order.setBuyer(buyer);
            order.setQuantity(orderDto.getQuantity());
            order.setTotalAmount(product.getPrice() * orderDto.getQuantity());
            order.setOrderStatus(Order.OrderStatus.PENDING);
            order.setPaid(false);

            return OrderDto.fromEntity(orderRepository.save(order));
        }

        // 주문 조회
    public OrderDto getOrderById(Long orderId, String name) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        ShopEntity shop = order.getProduct().getShop();
        if (shop.getOwner().getUsername().equals(name)) {
            return OrderDto.fromEntity(orderRepository.findById(orderId).orElseThrow());
        }
        return null;
    }
    // 주문 취소
    public OrderDto cancelOrder(Long orderId, String name) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        UserEntity user = userRepository.findByUsername(name).orElseThrow();
        if (order.getBuyer().getId().equals(user.getId()) && order.getOrderStatus() == Order.OrderStatus.PENDING) {
            order.setOrderStatus(Order.OrderStatus.CANCELED);
            orderRepository.save(order);
            return OrderDto.fromEntity(order);
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot cancel this order");
    }

    // 주문 결제
    public OrderDto payForOrder(Long orderId, String name) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        UserEntity user = userRepository.findByUsername(name).orElseThrow();
        if (order.getBuyer().getId().equals(user.getId()) && order.getOrderStatus() == Order.OrderStatus.PENDING) {
            order.setPaid(true);
            order.setOrderStatus(Order.OrderStatus.ACCEPTED);
            Product product = order.getProduct();
            product.setStock(product.getStock() - order.getQuantity());

            orderRepository.save(order);

            return OrderDto.fromEntity(order);
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot pay for this order");
    }
    }


