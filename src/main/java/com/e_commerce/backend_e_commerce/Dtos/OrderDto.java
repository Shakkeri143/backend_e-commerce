package com.e_commerce.backend_e_commerce.Dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.e_commerce.backend_e_commerce.Model.OrderItem;
import com.e_commerce.backend_e_commerce.Model.User;
import com.e_commerce.backend_e_commerce.Model.Enums.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

	
	private Long id;
	
	private Long userId;
	
	
	
	private double totalPrice;
	
	private Status status;
	
	private LocalDateTime orderDate;
	
	
	private List<OrderItemDto> orderItems;
}
