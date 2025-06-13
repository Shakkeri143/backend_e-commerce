package com.e_commerce.backend_e_commerce.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.convert.WritingConverter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	private String fromEmail;
	
	public void sendPaymentSuccessEmail(String toEmail,String orderId,Double amount) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage(); // creates the actual email message
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(fromEmail); // the sender (your app)
        helper.setTo(toEmail);     // the user who made the payment
        helper.setSubject("Payment Confirmation - Order #" + orderId);
        helper.setText(
            "<h2>Payment Successful!</h2>" +
            "<p>Your payment for order <strong>" + orderId + "</strong> has been processed successfully.</p>" +
            "<p>Amount: $" + amount + "</p>",
            true // this tells Spring it's HTML content
        );

        mailSender.send(message); // actually sends the email
	}
}
