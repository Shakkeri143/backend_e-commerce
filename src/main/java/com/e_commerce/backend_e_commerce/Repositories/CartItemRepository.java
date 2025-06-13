package com.e_commerce.backend_e_commerce.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_commerce.backend_e_commerce.Model.Cart;
import com.e_commerce.backend_e_commerce.Model.CartItem;
import com.e_commerce.backend_e_commerce.Model.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

	Optional<CartItem> findByCartAndProduct(Cart cart, Product product);


}
