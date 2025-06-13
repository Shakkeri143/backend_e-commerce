package com.e_commerce.backend_e_commerce.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e_commerce.backend_e_commerce.Dtos.OrderDto;
import com.e_commerce.backend_e_commerce.Dtos.OrderItemDto;
import com.e_commerce.backend_e_commerce.Exceptions.BadRequestException;
import com.e_commerce.backend_e_commerce.Exceptions.UserNotFoundException;
import com.e_commerce.backend_e_commerce.Model.Cart;
import com.e_commerce.backend_e_commerce.Model.CartItem;
import com.e_commerce.backend_e_commerce.Model.Order;
import com.e_commerce.backend_e_commerce.Model.OrderItem;
import com.e_commerce.backend_e_commerce.Model.Product;
import com.e_commerce.backend_e_commerce.Model.User;
import com.e_commerce.backend_e_commerce.Model.Enums.Status;
import com.e_commerce.backend_e_commerce.Repositories.CartItemRepository;
import com.e_commerce.backend_e_commerce.Repositories.CartRepository;
import com.e_commerce.backend_e_commerce.Repositories.OrderItemRepository;
import com.e_commerce.backend_e_commerce.Repositories.OrderRepository;
import com.e_commerce.backend_e_commerce.Repositories.ProductRepository;
import com.e_commerce.backend_e_commerce.Repositories.UserRepository;

@Service
public class OrderService {
	
//	private ProductRepository productRepository;
//	
//	private UserRepository userRepository;
//	
//	private CartRepository cartRepository;
//	
//	private CartItemRepository cartItemRepository;
//	
//	private OrderRepository orderRepository;
//	
//	
//	public OrderService(ProductRepository productRepository, UserRepository userRepository,
//			CartRepository cartRepository, CartItemRepository cartItemRepository, OrderRepository orderRepository) {
//		super();
//		this.productRepository = productRepository;
//		this.userRepository = userRepository;
//		this.cartRepository = cartRepository;
//		this.cartItemRepository = cartItemRepository;
//		this.orderRepository = orderRepository;
//	}
//	
//	public OrderDto createAOrder(Long userId)  {
//		
////		// Validate user
////		User user=userRepository.findById(userId).
////				orElseThrow(() ->new  UserNotFoundException("User Not Found"));
////		 // Get cart
////		Cart cart=cartRepository.findById(userId).
////				orElseThrow(()-> new BadRequestException("cart not found"));
////		// Check if cart is empty
////		if(cart.getCartItems().isEmpty()) {
////			throw  new BadRequestException("cart is empty");
////		}
////		// Validate stock
////		for(CartItem cartItem:cart.getCartItems()) {
////			Product product=cartItem.getProduct();
////			if(cartItem.getQuantity()>product.getStock()) {
////				throw new BadRequestException("quantity is more than stock avaliable");
////			}
////		}
////		// Create order
////		Order order=new Order();
////		order.setUser(user);
////		order.setTotal_price(cart.getTotalprice());
////		order.setStatus(Status.PLACED);
////		// Create order items
////		List<OrderItem> orderItems = new ArrayList<>();
////		for(CartItem cartItem:cart.getCartItems()) {
////			OrderItem orderItem= new OrderItem();
////			orderItem.setOrder(order);
////			orderItem.setProduct(cartItem.getProduct());
////			orderItem.setQuantity(cartItem.getQuantity());
////			orderItem.setPrice(cartItem.getProduct().getPrice());
////			orderItems.add(orderItem);
////			// Update product stock
////			Product product=cartItem.getProduct();
////			product.setStock(product.getStock()-cartItem.getQuantity());
////			productRepository.save(product);
////		}	
////		order.setOrderItems(orderItems);
////		orderRepository.save(order);
////		// Clear cart	
////		cart.getCartItems().forEach(cartItem -> cartItemRepository.delete(cartItem));
////		cart.getCartItems().clear();
////		cart.setTotalprice(0.0);
////        cartRepository.save(cart);
////        return convertToOrderDto(order);
//		
//		// 1. Validate User
//	    User user = userRepository.findById(userId)
//	            .orElseThrow(() -> new UserNotFoundException("User Not Found"));
//
//	    // 2. Get Cart associated with the user
//	    Cart cart = cartRepository.findByUserId(userId)
//	            .orElseThrow(() -> new BadRequestException("Cart not found"));
//
//	    // 3. Check if Cart is empty
//	    if (cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
//	        throw new BadRequestException("Cart is empty");
//	    }
//
//	    // 4. Validate stock availability
//	    for (CartItem cartItem : cart.getCartItems()) {
//	        Product product = cartItem.getProduct();
//	        if (cartItem.getQuantity() > product.getStock()) {
//	            throw new BadRequestException("Quantity exceeds available stock for product: " + product.getName());
//	        }
//	    }
//
//	    // 5. Create Order
//	    Order order = new Order();
//	    order.setUser(user);
//	    order.setTotalPrice(cart.getTotalprice());
//	    order.setStatus(Status.PLACED);
//	    order.setOrderDate(LocalDateTime.now());
//
//	    List<OrderItem> orderItems = new ArrayList<>();
//
//	    for (CartItem cartItem : cart.getCartItems()) {
//	        Product product = cartItem.getProduct();
//
//	        // 6. Create OrderItem
//	        OrderItem orderItem = new OrderItem();
//	        orderItem.setOrder(order);
//	        orderItem.setProduct(product);
//	        orderItem.setQuantity(cartItem.getQuantity());
//	        orderItem.setPrice(product.getPrice());
//	        orderItems.add(orderItem);
//
//	        // 7. Update Product Stock
//	        product.setStock(product.getStock() - cartItem.getQuantity());
//	        productRepository.save(product);
//	    }
//
//	    // 8. Save Order and OrderItems
//	    order.setOrderItems(orderItems);
//	    orderRepository.save(order); // Cascade saves OrderItems
//
//	    // 9. Clear Cart
//	    cart.getCartItems().forEach(cartItemRepository::delete);
//	    cart.getCartItems().clear();
//	    cart.setTotalprice(0.0);
//	    cartRepository.save(cart);
//
//	    // 10. Return DTO
//	    return convertToOrderDto(order);
//	}
//	
//	
//	public OrderDto getOrderById(Long userId, Long orderId) {
//        
//
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
//
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new BadRequestException("Order not found with ID: " + orderId));
//
//        if (!order.getUser().getId().equals(userId)) {
//            
//            throw new BadRequestException("Order does not belong to user");
//        }
//
//        return convertToOrderDto(order);
//    }
//	
//	public List<OrderDto> getOrdersByUserId(Long userId) {
//        
//
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
//
//        List<Order> orders = orderRepository.findByUser(user);
//        return orders.stream().map(this::convertToOrderDto).collect(Collectors.toList());
//    }
//
//
//	
//
//
//	private OrderDto convertToOrderDto(Order order) {
//		OrderDto orderDto=new OrderDto();
//		orderDto.setId(order.getId());
//        orderDto.setUserId(order.getUser().getId());
//        orderDto.setTotalPrice(order.getTotalPrice());
//        orderDto.setStatus(order.getStatus());
//        orderDto.setOrderDate(order.getOrderDate());
//        orderDto.setOrderItems(order.getOrderItems().stream()
//                .map(this::convertToOrderItemDto)
//                .collect(Collectors.toList()));
//        return orderDto;
//	}
//
//	private OrderItemDto convertToOrderItemDto(OrderItem orderItem) {
//		OrderItemDto orderItemDto=new OrderItemDto();
//		orderItemDto.setId(orderItem.getId());
//        orderItemDto.setProductId(orderItem.getProduct().getId());
//        orderItemDto.setQuantity(orderItem.getQuantity());
//        orderItemDto.setProductPrice(orderItem.getProduct().getPrice());
//        return orderItemDto;
//	}
//	
//	
//	
//	
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public OrderDto createOrder(Long userId) {
        

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found with ID: " + userId));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new BadRequestException("Cart not found for user ID: " + userId));

        if (cart.getCartItems().isEmpty()) {
   throw new BadRequestException("Cannot create order: Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Status.PENDING);
        order.setOrderItems(new ArrayList<>());

        double totalPrice = 0.0;

        for (CartItem cartItem : cart.getCartItems()) {
            Product product = cartItem.getProduct();
            int quantity = cartItem.getQuantity();

            if (quantity > product.getStock()) {

                throw new BadRequestException("Insufficient stock for product: " + product.getName());
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItem.setProductprice(product.getPrice());
            order.getOrderItems().add(orderItem);

            totalPrice += quantity * product.getPrice();
            product.setStock(product.getStock() - quantity);
            productRepository.save(product);
        }

        order.setTotalPrice(totalPrice);
        orderRepository.save(order);

        // Clear cart
        cart.getCartItems().forEach(cartItemRepository::delete);
        cart.getCartItems().clear();
        cartRepository.save(cart);

        return convertToOrderDto(order);
    }
    
    @Transactional
    public void updateOrderStatus(Long orderId, String status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BadRequestException("Order not found with ID: " + orderId));

        // Convert the string to an enum value
        try {
            Status newStatus = Status.valueOf(status.toUpperCase()); // case-insensitive
            order.setStatus(newStatus);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid status value: " + status);
        }

        orderRepository.save(order);
    }


    public List<OrderDto> getOrdersByUserId(Long userId) {
        

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found with ID: " + userId));

        List<Order> orders = orderRepository.findByUser(user);
        List<OrderDto> orderDtos = new ArrayList<>();

        for (Order order : orders) {
            orderDtos.add(convertToOrderDto(order));
        }

        return orderDtos;
    }

    public OrderDto getOrderById(Long userId, Long orderId) {
        

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found with ID: " + userId));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BadRequestException("Order not found with ID: " + orderId));

        if (!order.getUser().getId().equals(userId)) {
            
            throw new BadRequestException("Order does not belong to user");
        }

        return convertToOrderDto(order);
    }

    private OrderDto convertToOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setUserId(order.getUser().getId());
        orderDto.setTotalPrice(order.getTotalPrice());
        orderDto.setStatus(order.getStatus());
        orderDto.setOrderDate(order.getOrderDate());

        List<OrderItemDto> itemDtos = new ArrayList<>();
        for (OrderItem item : order.getOrderItems()) {
            OrderItemDto itemDto = new OrderItemDto();
            itemDto.setId(item.getId());
            itemDto.setProductId(item.getProduct().getId());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setProductPrice(item.getProductprice());
            itemDtos.add(itemDto);
        }

        orderDto.setOrderItems(itemDtos);
        return orderDto;
    }
}
