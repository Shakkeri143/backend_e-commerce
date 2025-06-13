package com.e_commerce.backend_e_commerce.Dtos;

import com.e_commerce.backend_e_commerce.Model.Order;
import com.e_commerce.backend_e_commerce.Model.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {

	private Long id;

	private Long productId;

	private Integer quantity;

	private double productPrice;
}
