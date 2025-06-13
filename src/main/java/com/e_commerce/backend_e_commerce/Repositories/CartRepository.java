package com.e_commerce.backend_e_commerce.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_commerce.backend_e_commerce.Model.Cart;
import com.e_commerce.backend_e_commerce.Model.CartItem;
import com.e_commerce.backend_e_commerce.Model.Product;
import com.e_commerce.backend_e_commerce.Model.User;


public interface CartRepository  extends JpaRepository<Cart,Long>{

	Optional<Cart> findByUser(User user);

	Optional<Cart> findByUserId(Long userId);
	 

}
