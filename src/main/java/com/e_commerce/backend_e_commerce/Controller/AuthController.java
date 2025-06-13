package com.e_commerce.backend_e_commerce.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_commerce.backend_e_commerce.Security.AuthRequest;
import com.e_commerce.backend_e_commerce.Security.AuthResponce;
import com.e_commerce.backend_e_commerce.Security.AuthService;
import com.e_commerce.backend_e_commerce.Security.RegisterRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponce> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponce> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
