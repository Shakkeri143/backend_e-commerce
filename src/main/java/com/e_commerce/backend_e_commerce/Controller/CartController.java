package com.e_commerce.backend_e_commerce.Controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.e_commerce.backend_e_commerce.Dtos.CartDto;
import com.e_commerce.backend_e_commerce.Dtos.CartItemDto;
import com.e_commerce.backend_e_commerce.Exceptions.BadRequestException;
import com.e_commerce.backend_e_commerce.Model.User;
import com.e_commerce.backend_e_commerce.Repositories.UserRepository;
import com.e_commerce.backend_e_commerce.Service.CartService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cart")

public class CartController {
	
	
	@Autowired
    private CartService cartService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<CartDto> getCart(Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);
        CartDto cartDto = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cartDto);
    }
    
    @GetMapping("/Allcarts")
    public List<CartDto> getAllCarts(Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);
        List<CartDto> cartDto = cartService.getAllCarts();
        return cartDto ;
    }

    @PostMapping
    public ResponseEntity<CartDto> addItemToCart(
            @RequestBody CartItemDto cartItemDto,
            Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);
        CartDto cartDto = cartService.addItemToCart(userId, cartItemDto);
        return ResponseEntity.ok(cartDto);
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartDto> updateCartItem(
            @PathVariable Long cartItemId,
            @RequestBody CartItemDto cartItemDto,
            Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);
        CartDto cartDto = cartService.updateTheCart(userId, cartItemId, cartItemDto);
        return ResponseEntity.ok(cartDto);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<CartDto> removeItemFromCart(
            @PathVariable Long cartItemId,
            Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);
        CartDto cartDto = cartService.removeItemFromCart(userId, cartItemId);
        return ResponseEntity.ok(cartDto);
    }

    private Long getUserIdFromAuthentication(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return user.getId();
    }

}
