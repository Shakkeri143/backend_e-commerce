package com.e_commerce.backend_e_commerce.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_commerce.backend_e_commerce.Dtos.OrderDto;
import com.e_commerce.backend_e_commerce.Model.User;
import com.e_commerce.backend_e_commerce.Repositories.UserRepository;
import com.e_commerce.backend_e_commerce.Service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	@Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);
        OrderDto orderDto = orderService.createOrder(userId);
        return ResponseEntity.ok(orderDto);
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrders(Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);
        List<OrderDto> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(
            @PathVariable Long orderId,
            Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);
        OrderDto orderDto = orderService.getOrderById(userId, orderId);
        return ResponseEntity.ok(orderDto);
    }

    private Long getUserIdFromAuthentication(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return user.getId();
    }
}
