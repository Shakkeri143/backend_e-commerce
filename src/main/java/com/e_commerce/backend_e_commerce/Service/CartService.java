package com.e_commerce.backend_e_commerce.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e_commerce.backend_e_commerce.Dtos.CartDto;
import com.e_commerce.backend_e_commerce.Dtos.CartItemDto;
import com.e_commerce.backend_e_commerce.Exceptions.BadRequestException;
import com.e_commerce.backend_e_commerce.Exceptions.ProductNotFoundException;
import com.e_commerce.backend_e_commerce.Exceptions.UserNotFoundException;
import com.e_commerce.backend_e_commerce.Model.Cart;
import com.e_commerce.backend_e_commerce.Model.CartItem;
import com.e_commerce.backend_e_commerce.Model.Product;
import com.e_commerce.backend_e_commerce.Model.User;
import com.e_commerce.backend_e_commerce.Repositories.CartItemRepository;
import com.e_commerce.backend_e_commerce.Repositories.CartRepository;
import com.e_commerce.backend_e_commerce.Repositories.ProductRepository;
import com.e_commerce.backend_e_commerce.Repositories.UserRepository;

@Service
public class CartService {

//	private UserRepository userRepository;
//	private CartRepository cartRepository;
//	private ProductRepository productRepository;
//	private CartItemRepository cartItemRepository;
//
//	public CartService(UserRepository userRepository, CartRepository cartRepository,
//			ProductRepository productRepository, CartItemRepository cartItemRepository) {
//		
//		this.userRepository = userRepository;
//		this.cartRepository = cartRepository;
//		this.productRepository = productRepository;
//		this.cartItemRepository = cartItemRepository;
//	}
//
//	public CartDto getCartByUserId(Long userid) {
//		User user = userRepository.findById(userid).orElseThrow(() -> new UserNotFoundException("User not Found"));
//		Cart cart = cartRepository.findByUser(user).orElseGet(() -> createACartForUser(user));
//		return convertToCartDto(cart);
//	}
//	
//	@Transactional
//	public CartDto addItemToCart(Long userid, CartItemDto cartItemDto)  {
////		
////		log.info("Adding item to cart for userId: {}, productId: {}, quantity: {}",
////                userId, cartItemDto.getProductId(), cartItemDto.getQuantity());
////        if (cartItemDto.getProductId() == null) {
////            log.error("ProductId is null in CartItemDto");
////            throw new BadRequestException("Product ID is required");
////        }
//		User user = userRepository.findById(userid).orElseThrow(() -> new UserNotFoundException("User not Found"));
//
//		Product product = productRepository.findById(cartItemDto.getProductId())
//				.orElseThrow(() -> new ProductNotFoundException("Product Not Found"));
//		
//		if (cartItemDto.getQuantity() <= 0) {
//            throw new BadRequestException("Quantity must be positive");
//        }
//
//		// stock avaliablitity
//		if (cartItemDto.getQuantity() > product.getStock()) {
//			throw new BadRequestException("requested quantity exceeded the avaliable quantity");
//		}
//
//		// creating the cart if cart is not present
//		Cart cart = cartRepository.findByUser(user).orElseGet(() -> createACartForUser(user));
//
////		The method checks if the product already exists in the user's cart by
////		filtering through the cart's cartItems and checking if the product ID 
////		matches the one in cartItemDTO.
//
////		If an existing CartItem is found, it will update the quantity; 
////		otherwise, it will create a new CartItem.
//
//		CartItem existingCartItem = cart.getCartItems().stream()
//				.filter(item -> item.getProduct().getId() == cartItemDto.getProductId()) // Comparison using `==`
//				.findFirst() // Properly closing the `findFirst` method
//				.orElse(null); // Defaulting to `null` if no matching item is found
//
//		if (existingCartItem != null) {
//			Integer newQuantity =   (existingCartItem.getQuantity() + cartItemDto.getQuantity());
//            if (newQuantity > product.getStock()) {
//                throw new BadRequestException("Total quantity exceeds available stock");
//            }
//			
//			existingCartItem.setQuantity(newQuantity);
//			cartItemRepository.save(existingCartItem);
//		} else {
//			CartItem cartItem = new CartItem();
//			
//			cartItem.setCart(cart);
//			cartItem.setProduct(product);
//			cartItem.setQuantity(cartItemDto.getQuantity());
//			cart.getCartItems().add(cartItem);
//			cartItemRepository.save(cartItem);
//		}
//		cart.setTotalprice(calculateTotalPrice(cart));
//		cartRepository.save(cart);
//		return convertToCartDto(cart);
//	}
//	@Transactional
//	public CartDto updateTheCart(Long userId, Long itemId, Integer quantity)  {
//		Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new BadRequestException("cart Not found"));
//		CartItem cartItem = cartItemRepository.findById(itemId)
//				.orElseThrow(() -> new ProductNotFoundException("product not found"));
//
//		if (cartItem.getCart() == null || !cartItem.getCart().getId().equals(cart.getId())) {
//			throw new BadRequestException("the cart is not matched");
//		}
//
//		if (quantity <= 0) {
//			cart.getCartItems().remove(cartItem);
//			cartItemRepository.delete(cartItem);
//		} else {
//			if (quantity > cartItem.getProduct().getStock()) {
//				throw new BadRequestException("stock is limited");
//			}
//			cartItem.setQuantity(quantity);
//			cartItemRepository.save(cartItem);
//		}
//		cart.setTotalprice(calculateTotalPrice(cart));
//		cartRepository.save(cart);
//		return convertToCartDto(cart);
//	}
//	@Transactional
//	public CartDto removeItemFromCart(Long userId, Long itemId)  {
//
//		Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new BadRequestException("cart Not found"));
//
//		CartItem cartItem = cartItemRepository.findById(itemId)
//				.orElseThrow(() -> new ProductNotFoundException("product not found"));
//
//		if (!cart.getCartItems().contains(cartItem)) {
//	        throw new BadRequestException("The item does not belong to the user's cart");
//	    }
//		cart.getCartItems().remove(cartItem);
//		cartItemRepository.delete(cartItem);
//		cart.setTotalprice(calculateTotalPrice(cart));
//		cartRepository.save(cart);
//		return convertToCartDto(cart);
//	}
//
//	private double calculateTotalPrice(Cart cart) {
//		return cart.getCartItems().stream().mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice())
//				.sum();
//	}
//
//	private Cart createACartForUser(User user) {
//		Cart cart = new Cart();
//		cart.setUser(user);
//		return cartRepository.save(cart);
//	}
//
//	private CartDto convertToCartDto(Cart cart) {
//		CartDto cartDto = new CartDto();
//		cartDto.setId(cart.getId());
//		cartDto.setUserId(cart.getUser().getId());
//		cartDto.setTotalPrice(cart.getTotalprice());
//		cartDto.setCartItems(
//			    (cart.getCartItems() != null) 
//			    ? cart.getCartItems().stream().map(this::convertToCartItemDto).collect(Collectors.toList()) 
//			    : new ArrayList<>()
//			);
//
//		return cartDto;
//	}
//
//	private CartItemDto convertToCartItemDto(CartItem cartitem) {
//		CartItemDto cartItemDto = new CartItemDto();
//		cartItemDto.setId(cartitem.getId());
//		
//		cartItemDto.setProductId(cartitem.getProduct().getId());
//        cartItemDto.setProductName(cartitem.getProduct().getName());
//        cartItemDto.setProductPrice(cartitem.getProduct().getPrice());
//		cartItemDto.setQuantity(cartitem.getQuantity());
//		return cartItemDto;
//	}
//
//	public List<CartDto> getAllCarts() {
//	    List<Cart> carts = cartRepository.findAll();
//	    return carts.stream().map(this::convertToCartDto).collect(Collectors.toList());
//	}
	
	@Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    public CartDto getCartByUserId(Long userId) {
        

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found with ID: " + userId));

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setCartItems(new ArrayList<>());
                    return cartRepository.save(newCart);
                });

        return convertToCartDto(cart);
    }

    @Transactional
    public CartDto addItemToCart(Long userId, CartItemDto cartItemDto) {
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found with ID: " + userId));

        Product product = productRepository.findById(cartItemDto.getProductId())
                .orElseThrow(() -> new BadRequestException("Product not found with ID: " + cartItemDto.getProductId()));

        if (cartItemDto.getQuantity() <= 0 || cartItemDto.getQuantity() > product.getStock()) {
            
            throw new BadRequestException("Invalid quantity or insufficient stock");
        }

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setCartItems(new ArrayList<>());
                    return cartRepository.save(newCart);
                });

        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(cartItemDto.getProductId()))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            int newQuantity = cartItem.getQuantity() + cartItemDto.getQuantity();
            if (newQuantity > product.getStock()) {
                
                throw new BadRequestException("Total quantity exceeds available stock");
            }
            cartItem.setQuantity(newQuantity);
            cartItemRepository.save(cartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setProductName(product.getName());
            cartItem.setQuantity(cartItemDto.getQuantity());
            cart.getCartItems().add(cartItem);
            cartItemRepository.save(cartItem);
        }

        cartRepository.save(cart);
        return convertToCartDto(cart);
    }

    @Transactional
    public CartDto updateTheCart(Long userId, Long cartItemId, CartItemDto cartItemDto) {
        

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found with ID: " + userId));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new BadRequestException("Cart not found for user ID: " + userId));

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new BadRequestException("Cart item not found with ID: " + cartItemId));

        if (!cartItem.getCart().getId().equals(cart.getId())) {
            
            throw new BadRequestException("Cart item does not belong to user");
        }

        Product product = productRepository.findById(cartItem.getProduct().getId())
                .orElseThrow(() -> new BadRequestException("Product not found"));

        if (cartItemDto.getQuantity() <= 0 || cartItemDto.getQuantity() > product.getStock()) {
            
            throw new BadRequestException("Invalid quantity or insufficient stock");
        }

        cartItem.setQuantity(cartItemDto.getQuantity());
        cartItemRepository.save(cartItem);

        return convertToCartDto(cart);
    }

    @Transactional
    public CartDto removeItemFromCart(Long userId, Long cartItemId) {
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found with ID: " + userId));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new BadRequestException("Cart not found for user ID: " + userId));

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new BadRequestException("Cart item not found with ID: " + cartItemId));

        if (!cartItem.getCart().getId().equals(cart.getId())) {
            
            throw new BadRequestException("Cart item does not belong to user");
        }

        cart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);
        cartRepository.save(cart);

        return convertToCartDto(cart);
    }

    private CartDto convertToCartDto(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setId(cart.getId());
        cartDto.setUserId(cart.getUser().getId());
        List<CartItemDto> itemDtos = new ArrayList<>();
        double totalPrice = 0.0;

        for (CartItem item : cart.getCartItems()) {
            CartItemDto itemDto = new CartItemDto();
            itemDto.setId(item.getId());
            itemDto.setProductId(item.getProduct().getId());
            itemDto.setProductName(item.getProductName());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setProductPrice(item.getProduct().getPrice());
            itemDtos.add(itemDto);
            totalPrice += item.getQuantity() * item.getProduct().getPrice();
        }

        cartDto.setCartItems(itemDtos);
        cartDto.setTotalPrice(totalPrice);
        return cartDto;
    }
    
    private List<CartDto> convertToCartDto(List<Cart> cartList) {
        return cartList.stream()
                .map(this::convertToCartDto)
                .collect(Collectors.toList());
    }


	public List<CartDto> getAllCarts() {
		List<Cart> allCart=cartRepository.findAll();
		return convertToCartDto(allCart);
	}

}
