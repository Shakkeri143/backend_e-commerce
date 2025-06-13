package com.e_commerce.backend_e_commerce.Dtos;

import java.util.List;

import jakarta.annotation.sql.DataSourceDefinition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
	private Long id;
    private Long userId;
    private Double totalPrice;
    private List<CartItemDto> cartItems;
}
