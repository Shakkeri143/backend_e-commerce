package com.e_commerce.backend_e_commerce.Controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_commerce.backend_e_commerce.Dtos.PaymentDto;
import com.e_commerce.backend_e_commerce.Dtos.PaymentRequestDto;
import com.e_commerce.backend_e_commerce.Model.User;
import com.e_commerce.backend_e_commerce.Repositories.UserRepository;
import com.e_commerce.backend_e_commerce.Service.PaymentService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
	private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping(value = "/{orderId}/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentDto> createPaymentIntent(
            @PathVariable Long orderId,
            @RequestBody PaymentRequest paymentRequest) {
        PaymentDto paymentDto = paymentService.createPaymentIntent(
                orderId, paymentRequest.getAmount(), paymentRequest.getCurrency());
        return ResponseEntity.ok(paymentDto);
    }

    @PostMapping("/{paymentId}/confirm")
    public ResponseEntity<PaymentDto> confirmPayment(
            @PathVariable String paymentId,
            @RequestBody PaymentConfirmRequest confirmRequest) throws MessagingException {
        PaymentDto paymentDto = paymentService.confirmPayment(paymentId, confirmRequest.getPaymentMethodId());
        return ResponseEntity.ok(paymentDto);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<PaymentDto> getPayment(@PathVariable Long orderId) {
        PaymentDto paymentDto = paymentService.getPaymentByOrderId(orderId);
        return ResponseEntity.ok(paymentDto);
    }
	
}

class PaymentRequest {
    private Double amount;
    private String currency;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}

class PaymentConfirmRequest {
    private String paymentMethodId;

    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }
}
