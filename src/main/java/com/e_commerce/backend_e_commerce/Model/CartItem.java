package com.e_commerce.backend_e_commerce.Model;

import com.e_commerce.backend_e_commerce.Dtos.CartDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "cart_id")
	@JsonIgnore
	private Cart cart;
	
	@ManyToOne
    @JoinColumn(name = "product_id")
	private Product product ;
	
	private String productName;
	
	private Integer quantity;
	

}
