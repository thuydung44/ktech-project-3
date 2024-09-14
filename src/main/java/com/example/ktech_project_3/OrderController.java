

package com.example.ktech_project_3;

import com.example.ktech_project_3.dto.OrderDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping("/create")
    public OrderDto createOrder(
            @RequestBody
            OrderDto orderDto) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return orderService.createOrder(orderDto, name);
    }
    @GetMapping("/{orderId}")
    public OrderDto getOrder(@PathVariable Long orderId) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return orderService.getOrderById(orderId, name);
    }

    @PutMapping("/{orderId}/cancel")
    public OrderDto cancelOrder(@PathVariable Long orderId) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return orderService.cancelOrder(orderId, name);
    }

    @PostMapping("/{orderId}/payment")
    public OrderDto paymentOrder(@PathVariable Long orderId) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return orderService.payForOrder(orderId, name);
    }

}

