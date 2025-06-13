package com.e_commerce.backend_e_commerce.Service;

import java.time.LocalDateTime;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e_commerce.backend_e_commerce.Dtos.PaymentDto;
import com.e_commerce.backend_e_commerce.Exceptions.BadRequestException;
import com.e_commerce.backend_e_commerce.Model.Order;
import com.e_commerce.backend_e_commerce.Model.Payment;
import com.e_commerce.backend_e_commerce.Model.User;
import com.e_commerce.backend_e_commerce.Model.Enums.Status;
import com.e_commerce.backend_e_commerce.Repositories.OrderRepository;
import com.e_commerce.backend_e_commerce.Repositories.PaymentRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import jakarta.mail.MessagingException;

import com.stripe.param.PaymentIntentConfirmParams;


@Service
public class PaymentService {

	@Value("${stripe.api.key}")
    private String stripeApiKey;
	
	@Autowired
	private EmailService emailService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    

    
    
    @Transactional
    public PaymentDto createPaymentIntent(Long orderId, Double amount, String currency) {
        Stripe.apiKey = stripeApiKey;
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BadRequestException("Order not found with ID: " + orderId));

        if (!order.getStatus().equals(Status.PENDING)) {
            throw new BadRequestException("Order is not in PENDING status");
        }

        if (amount <= 0 || !amount.equals(order.getTotalPrice())) {
            throw new BadRequestException("Invalid payment amount");
        }

        try {
            // Build the creation parameters
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount((long) (amount * 100)) // Convert to cents
                    .setCurrency(currency)
                    .setAutomaticPaymentMethods(
                    	    PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                    	        .setEnabled(true)
                    	        .setAllowRedirects(
                    	            PaymentIntentCreateParams.AutomaticPaymentMethods.AllowRedirects.NEVER)
                    	        .build()
                    	)
                    .build();

            // Actually create the PaymentIntent with Stripe
            PaymentIntent paymentIntent = PaymentIntent.create(params);

            // Save to DB
            Payment payment = new Payment();
            payment.setStripePaymentId(paymentIntent.getId());
            payment.setOrder(order);
            payment.setPaymentDate(LocalDateTime.now());
            payment.setAmount(amount);
            payment.setCurrency(currency);
            payment.setStatus(paymentIntent.getStatus());
            paymentRepository.save(payment);

            return convertToPaymentDto(payment, paymentIntent.getClientSecret());
        } catch (StripeException e) {
            throw new BadRequestException("Failed to create payment intent: " + e.getMessage());
        }
    }

    
    @Transactional
    public PaymentDto confirmPayment(String stripePaymentId, String paymentMethodId) throws MessagingException {
        Stripe.apiKey = stripeApiKey;
        Payment payment = paymentRepository.findByStripePaymentId(stripePaymentId)
                .orElseThrow(() -> new BadRequestException("Payment not found with ID: " + stripePaymentId));

        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(stripePaymentId);
            if ("succeeded".equals(paymentIntent.getStatus())) {
                throw new BadRequestException("Payment already succeeded");
            }

            PaymentIntentConfirmParams confirmParams = PaymentIntentConfirmParams.builder()
            	    .setPaymentMethod(paymentMethodId)
            	    .setReturnUrl("https://example.com/dummy-return") // ✅ Dummy URL
            	    .build();

            	paymentIntent = paymentIntent.confirm(confirmParams);


            payment.setStatus(paymentIntent.getStatus());
            paymentRepository.save(payment);

            if ("succeeded".equals(paymentIntent.getStatus())) {
                Order order = payment.getOrder();
                order.setStatus(Status.PLACED);
                orderRepository.save(order);
                
                User user = order.getUser();
                if (user != null && user.getEmail() != null) {
                    emailService.sendPaymentSuccessEmail(user.getEmail(), order.getId().toString(), payment.getAmount());
                }
            }

            return convertToPaymentDto(payment, null);
        } catch (StripeException e) {
            
            payment.setStatus("failed");
            paymentRepository.save(payment);
            throw new BadRequestException("Payment confirmation failed: " + e.getMessage());
        }
    }
    
    public PaymentDto getPaymentByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BadRequestException("Order not found with ID: " + orderId));
        Payment payment = paymentRepository.findByOrder(order)
                .orElseThrow(() -> new BadRequestException("Payment not found for order ID: " + orderId));
        return convertToPaymentDto(payment, null);
    }

    private PaymentDto convertToPaymentDto(Payment payment, String clientSecret) {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setId(payment.getId());
        paymentDto.setStripePaymentId(payment.getStripePaymentId());
        paymentDto.setOrderId(payment.getOrder().getId());
        paymentDto.setAmount(payment.getAmount());
        paymentDto.setCurrency(payment.getCurrency());
        paymentDto.setStatus(payment.getStatus());
        paymentDto.setClientSecret(clientSecret);
        return paymentDto;
    }
    
    

    
}
