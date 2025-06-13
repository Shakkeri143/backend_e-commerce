package com.e_commerce.backend_e_commerce.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_commerce.backend_e_commerce.Model.Order;
import com.e_commerce.backend_e_commerce.Model.User;


public interface OrderRepository extends JpaRepository<Order,Long>{

	List<Order> findByUser(User user);

}
