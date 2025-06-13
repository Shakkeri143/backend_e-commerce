package com.e_commerce.backend_e_commerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class BackendECommerceApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		
		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
		SpringApplication.run(BackendECommerceApplication.class, args);
	}

}
