package com.e_commerce.backend_e_commerce.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.e_commerce.backend_e_commerce.Dtos.CartDto;
import com.e_commerce.backend_e_commerce.Dtos.CartItemDto;
import com.e_commerce.backend_e_commerce.Model.Cart;
import com.e_commerce.backend_e_commerce.Model.CartItem;
import com.e_commerce.backend_e_commerce.Model.Product;
import com.e_commerce.backend_e_commerce.Model.User;
import com.e_commerce.backend_e_commerce.Repositories.CartItemRepository;
import com.e_commerce.backend_e_commerce.Repositories.CartRepository;
import com.e_commerce.backend_e_commerce.Repositories.ProductRepository;
import com.e_commerce.backend_e_commerce.Repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {
	@Mock
	UserRepository userRepository;
	@Mock
	 CartRepository cartRepository;
	@Mock
	 ProductRepository productRepository;
	@Mock
	 CartItemRepository cartItemRepository;
	
	@InjectMocks
	CartService cartService;
	
	private User user;
	private Product product;
	private Product product2;
	private Cart cart;
	private CartItem cartItem;
	private CartItemDto cartItemDto;
	
	@BeforeEach
	void setup() {
//		user=new User();
//		user.setId(3L);
//		user.setName("userName");
//		
//		cart = new Cart();
//        cart.setId(1L);
//        cart.setUser(user);
//        cart.setTotalprice(0.0);
//        cart.setCartItems(new ArrayList<>());
//        
//        product1=new Product();
//        product1.setId(1L);
//        product1.setName("bluetooth");
//        product1.setPrice(100);
//        product1.setStock(50);
//        
//        product2=new Product();
//        product2.setId(2L);
//        product2.setName("iphone");
//        product2.setPrice(20);
//        product2.setStock(10);
//        
//        cartItem=new CartItem();
//        cartItem.setId(1L);
//        cartItem.setCart(cart);
//        cartItem.setProduct(product2);
//        cartItem.setQuantity(2);
//        
//        cartItemDto=new CartItemDto();
//        cartItemDto.setProductId(2L);
//        cartItemDto.setQuantity(2);
        
		
	        user = new User();
	        user.setId(1L);
	        user.setName("userName");

	        product = new Product();
	        product.setId(1L);
	        product.setName("product");
	        product.setPrice(100.0);
	        product.setStock(50);

	        cart = new Cart();
	        cart.setId(1L);
	        cart.setUser(user);
	        cart.setTotalprice(0.0);

	        cartItem = new CartItem();
	        cartItem.setId(1L);
	        cartItem.setCart(cart);
	        cartItem.setProduct(product);
	        cartItem.setQuantity(2);

	        cartItemDto = new CartItemDto();
	        cartItemDto.setProductId(1L);
	        cartItemDto.setQuantity(2);
	    
	}
	
//	@Test
//	void getCartByUserId_existingCart_returnsCartDto() {
//		
//		when(userRepository.findById(3L)).thenReturn(Optional.of(user));
//		when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));
//		
//		
//		CartDto result= cartService.getCartByUserId(3L);
//		
//		assertNotNull(result);
//		assertEquals(1L, result.getId());
//		assertEquals(3L, result.getUserId());
//		assertEquals(0.0, result.getTotalPrice());
//		assertTrue(result.getCartItems().isEmpty());
//		
//		verify(userRepository).findById(3L);
//		verify(cartRepository).findByUser(user);
//	}
	
	
//	@Test
//	void addItemToCart__AndReturn_CartDto() {
//		
//		when(userRepository.findById(3L)).thenReturn(Optional.of(user));
//		when(productRepository.findById(2L)).thenReturn(Optional.of(product2));
//		when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));
//		when(cartItemRepository.findByCartAndProduct(cart, product2)).thenReturn(Optional.empty());
//		when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);
//		when(cartRepository.save(cart)).thenReturn(cart);
//		
//		
//	 CartDto result= cartService.addItemToCart(3L, cartItemDto);
//		
//		
//		assertNotNull(result);
//        assertEquals(1, result.getCartItems().size());
//        assertEquals(2L, result.getCartItems().get(0).getProductId());
//        assertEquals(2, result.getCartItems().get(0).getQuantity());
//        assertEquals(40, result.getTotalPrice());
//        verify(cartItemRepository).save(any(CartItem.class));
//        verify(cartRepository).save(cart);
//	}
	@Test
	void addItemToCart__AndReturn_CartDto() {
	    // Mocking the necessary repositories
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findByCartAndProduct(cart, product)).thenReturn(Optional.empty());
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);
        when(cartRepository.save(cart)).thenReturn(cart);

        // Act
        CartDto result = cartService.addItemToCart(1L, cartItemDto);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getCartItems().size());
        assertEquals(1L, result.getCartItems().get(0).getProductId());
        assertEquals(2, result.getCartItems().get(0).getQuantity());
        assertEquals(200.0, result.getTotalPrice());
        verify(cartItemRepository).save(any(CartItem.class));
        verify(cartRepository).save(cart);
    }
	}

	
	
	
	

