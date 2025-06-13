package com.e_commerce.backend_e_commerce.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.e_commerce.backend_e_commerce.Dtos.ProductDto;
import com.e_commerce.backend_e_commerce.Exceptions.ProductNotFoundException;
import com.e_commerce.backend_e_commerce.Model.Product;
import com.e_commerce.backend_e_commerce.Repositories.ProductRepository;

@Service
public class ProductService {
	
	private ProductRepository productRepository;
	
	public ProductService(ProductRepository productRepository) {
		this.productRepository=productRepository;
	}
	

	public List<ProductDto> getAllProducts() {
		return productRepository.findAll().stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
	}
	
	public ProductDto getProductById(long id) {
		Product product=productRepository.findById(id)
				.orElseThrow(() -> new ProductNotFoundException("Product Not found with the id: "+id) );
		return convertToDto(product);
	}
	
	public ProductDto addProduct(ProductDto productDto) {
        Product product = convertToProduct(productDto);
        Product savedProduct = productRepository.save(product);
        return convertToDto(savedProduct);
    }
	
	public ProductDto updateProduct(long id, ProductDto productDto) {
		Product existingProduct =productRepository.findById(id)
				.orElseThrow(() -> new ProductNotFoundException
						(("Product Not found with the id: "+id)));
		existingProduct.setName(productDto.getName());
		existingProduct.setDescription(productDto.getDescription());
		existingProduct.setPrice(productDto.getPrice());
		existingProduct.setStock(productDto.getStock());
		existingProduct.setCategory(productDto.getCategory());
		
		Product updatedProduct=productRepository.save(existingProduct);
		return convertToDto(updatedProduct);
	}


	public ProductDto convertToDto(Product product) {
		ProductDto dto=new ProductDto();
		dto.setId(product.getId());
		dto.setName(product.getName());
		dto.setDescription(product.getDescription());
		dto.setPrice(product.getPrice());
		dto.setStock(product.getStock());
		dto.setCategory(product.getCategory());
		return dto;
	}
	
	public Product convertToProduct(ProductDto productDto) {
		Product Product=new Product();
		Product.setName(productDto.getName());
		Product.setDescription(productDto.getDescription());
		Product.setPrice(productDto.getPrice());
		Product.setStock(productDto.getStock());
		Product.setCategory(productDto.getCategory());
		return Product;
	}


	public void deleteProduct(long id) {
	Product product=productRepository.findById(id)
			.orElseThrow(()->new 
					ProductNotFoundException("Product Not found with the id: "+id));
	
		productRepository.delete(product);
	}


	


	


	


	

}
