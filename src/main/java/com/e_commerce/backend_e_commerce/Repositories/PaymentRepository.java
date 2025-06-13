package com.e_commerce.backend_e_commerce.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_commerce.backend_e_commerce.Model.Order;
import com.e_commerce.backend_e_commerce.Model.Payment;

public interface PaymentRepository extends JpaRepository<Payment,Long>{
	Optional<Payment> findByStripePaymentId(String stripePaymentId);
    Optional<Payment> findByOrder(Order order);
}
