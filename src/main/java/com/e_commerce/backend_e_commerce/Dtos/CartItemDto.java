package com.e_commerce.backend_e_commerce.Dtos;

import com.e_commerce.backend_e_commerce.Model.Cart;
import com.e_commerce.backend_e_commerce.Model.Product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
	
	private Long id;
	
	private Long productId;

    private String productName;

    private Double productPrice;

	
	@NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
	private Integer quantity;
}
