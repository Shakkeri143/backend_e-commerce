package com.e_commerce.backend_e_commerce.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_commerce.backend_e_commerce.Dtos.ProductDto;
import com.e_commerce.backend_e_commerce.Service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/product")
public class ProductController {
	
	private ProductService productService;
	
public ProductController(ProductService productService) {
		this.productService = productService;
	}

//	Getallproducts
//	getProduct
//	addProductById
//	Updateproduct
//	deleteProduct
//	Dto to entity
//	Entity to dot
	
	@GetMapping()
	public ResponseEntity<List<ProductDto>> getAllProducts(){
		return ResponseEntity.ok(productService.getAllProducts());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductDto> getProductById(@Valid @PathVariable Long id){
		return ResponseEntity.ok(productService.getProductById(id));
	}
	
	@PostMapping()
	public ResponseEntity<ProductDto> addProduct(@Valid @RequestBody ProductDto productDto){
		return ResponseEntity.status(201).body(productService.addProduct(productDto));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id,@Valid @RequestBody ProductDto productDto){
		return ResponseEntity.status(201).body(productService.updateProduct(id,productDto));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long id){
		productService.deleteProduct(id);
		return new ResponseEntity<>("Deleted the product Sucessfully", HttpStatus.OK);
	}

}
