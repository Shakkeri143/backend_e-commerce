package com.e_commerce.backend_e_commerce.Dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
	
	private Long id;
    private String stripePaymentId;
    private Long orderId;
    private Double amount;
    private String currency;
    private String status;
    private String clientSecret;

}
