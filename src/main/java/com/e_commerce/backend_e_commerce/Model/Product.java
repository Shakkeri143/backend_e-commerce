package com.e_commerce.backend_e_commerce.Model;

import com.e_commerce.backend_e_commerce.Exceptions.ProductNotFoundException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(max = 100)
	private String name;
	
	@NotNull
	@Size(max = 1000)
	private String description;
	
	@NotNull
	@Positive
	private double price;
	
	@NotNull
	@Min(0)
	private int stock;
	
	@NotNull
	@Size(max = 50)
	private String category;
	

	
	

}
