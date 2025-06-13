package com.e_commerce.backend_e_commerce.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_commerce.backend_e_commerce.Model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
