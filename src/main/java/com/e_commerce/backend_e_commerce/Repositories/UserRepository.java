package com.e_commerce.backend_e_commerce.Repositories;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.e_commerce.backend_e_commerce.Model.User;

public interface UserRepository  extends JpaRepository<User,Long>{

	Optional<User> findByEmail(String email);

}
