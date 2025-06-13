package com.e_commerce.backend_e_commerce.Dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDto {
	private String paymentMethodId; 
    private Long orderId;
    private Long userId;
}
