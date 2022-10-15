package my.Ecom.Services.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import my.Ecom.Exception.ResourceNotFoundException;
import my.Ecom.Models.Cart;
import my.Ecom.Models.CartItem;
import my.Ecom.Models.Order;
import my.Ecom.Models.OrderItem;
import my.Ecom.Models.Product;
import my.Ecom.Models.User;
import my.Ecom.Repositories.CartRepository;
import my.Ecom.Repositories.OrderRepository;
import my.Ecom.Repositories.ProductRepository;
import my.Ecom.Repositories.UserRepository;
import my.Ecom.Services.OrderService;
import my.Ecom.payload.OrderDto;
import my.Ecom.payload.OrderItemDto;
import my.Ecom.payload.OrderRequest;
import my.Ecom.payload.OrderResponse;
import my.Ecom.payload.ProductResponse;
import my.Ecom.payload.UserDto;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ModelMapper mapper;
	 
	@Autowired
	private ProductRepository productRepository;
	

	@Override
	public OrderDto createOrder(OrderRequest request, String username) {
	    int cartId=request.getCartId();
	    String address=request.getAddress();
	    
	    User user = this.userRepository.findByUserEmail(username).orElseThrow(()-> new ResourceNotFoundException());
	    
	    Cart cart = this.cartRepository.findByUser(user).orElseThrow(()-> new ResourceNotFoundException());
	    
	    Set<CartItem> items = cart.getItems();
	    
	    Order order=new Order();
	    
	    AtomicReference<Double> totalOrderPrice=new AtomicReference<>(0.0);
	    
	    Set<OrderItem> orderItems = items.stream().map((cartItem)->{
	    	
	    	OrderItem orderItem=new OrderItem();
	    	orderItem.setProduct(cartItem.getProduct());
	    	orderItem.setQuantity(cartItem.getQuantity());
	    	orderItem.setTotalProductPrice(cartItem.getTotalProductPrice());
	    	orderItem.setOrder(order);    	
	    	
	   
	    	totalOrderPrice.set(totalOrderPrice.get() + cartItem.getTotalProductPrice());
	    	
	    	//
	    	int productId=orderItem.getProduct().getProductId();
	    	//product:-fetch
	    	Product product = this.productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException());
	    	//update the product quantity
	    	int q=cartItem.getQuantity();
	    	product.setProductQuantity(product.getProductQuantity()-q);
	    	
	    	//save the product
	    	
	    	 this.productRepository.save(product);
	    	
	    	return orderItem;
	    	
	    }).collect(Collectors.toSet());
	    
	    order.setItems(orderItems);
	    order.setBillingAddress(address);
	    order.setUser(user);
	    order.setOrderCreated(new Date());
	    order.setOrderAmount(totalOrderPrice.get());
	    order.setOrderDelivered(null);
	    order.setOrderStatus("CREATED");
	    order.setPaymentStatus("Not PAID");
	    
	    Order savedOrder = this.orderRepository.save(order);
	    
	    cart.getItems().clear();
	    
	    this.cartRepository.save(cart);
	    
	    
	    return this.mapper.map(savedOrder, OrderDto.class);
	}

	@Override
	public OrderDto updateOrder(OrderDto orderDto, int orderId) {
	 Order order = this.orderRepository.findById(orderId).orElseThrow(()->new ResourceNotFoundException());
	//String orderStatus= orderDto.getOrderStatus();
	String paymentStatus= orderDto.getPaymentStatus();
	
	//order.setOrderStatus(orderStatus);
	order.setPaymentStatus(paymentStatus);
	Order updatedOrder = this.orderRepository.save(order);
	OrderDto orderDto2 = this.mapper.map(updatedOrder, OrderDto.class);
	return orderDto2;
	}

	@Override
	public void deleteOrder(int orderId) {
		Order order = this.orderRepository.findById(orderId).orElseThrow(()->new ResourceNotFoundException("order not found with this id"));
	//	List<Order> All = this.orderRepository.findAll();
		//All.removeIf(order.getOrderId()==All.)
		orderRepository.delete(order);
		
	}

	@Override
	public OrderResponse getAll(int pageNumber,int pageSize,String sortBy,String sortDir) {
		
		Sort sort=null;
		if(sortDir.trim().toLowerCase().equals("asc")) {
			sort=Sort.by(sortBy).ascending();
		}
		else {
			sort=Sort.by(sortBy).descending();
		}
		
		Pageable pageable=PageRequest.of(pageNumber, pageSize,sort);
		
		 Page<Order> page = this.orderRepository.findAll(pageable);
		 
		 List<Order> allOrders=page.getContent();
		 

		 List<OrderDto> dto=new ArrayList<OrderDto>();
		 
		
		 
	     for(Order d:allOrders) {
	    	 OrderDto ordto=new OrderDto();
	    	ordto.setOrderId(d.getOrderId());
	    	ordto.setPaymentStatus(d.getPaymentStatus());
	    	ordto.setOrderStatus(d.getOrderStatus());
	    	ordto.setOrderDelivered(d.getOrderDelivered());
	    	ordto.setOrderCreated(d.getOrderCreated());
	    	ordto.setOrderAmount(d.getOrderAmount());
	    	ordto.setBillingAddress(d.getBillingAddress());
	    	
	    	User user = d.getUser();	 
	    	UserDto userDto = this.mapper.map(user, UserDto.class);
	    	ordto.setUser(userDto);
	    	
	    	
	    	Set<OrderItem> orderItems = d.getItems();
	    	Set<OrderItemDto> orderItemDto = orderItems.stream().map(orderItem->(this.mapper.map(orderItem, OrderItemDto.class))).collect(Collectors.toSet());
	    	ordto.setItems(orderItemDto);
	    	
	    	
	    	dto.add(ordto);
	     }
	     

	    
	     
		 
	   /*  List<OrderDto> content = allOrders.stream().map((order)->this.mapper.map(allOrders, OrderDto.class)).collect(Collectors.toList());
		*/
	     
	     OrderResponse response=new OrderResponse();
	     response.setContent(dto);	     
	     response.setPageNumber(page.getNumber());
	     response.setPageSize(page.getSize());
	     response.setTotalElements(page.getTotalElements());
	     response.setTotalPages(page.getTotalPages());
	     response.setLastPage(page.isLast());
		return response;
	}

	@Override
	public OrderDto get(int orderId) {
		
		Order order = this.orderRepository.findById(orderId).orElseThrow(()->new ResourceNotFoundException("order not found with this id"));
		OrderDto ordetDto = this.mapper.map(order, OrderDto.class);
		
		return ordetDto;
	}
	
	

	@Override
	public OrderDto updateOrderStatus(int orderId) {
		
		Order order = this.orderRepository.findById(orderId).orElseThrow(()->new ResourceNotFoundException("order not found with this id"));
		order.setOrderStatus("DELIVERED");
		order.setOrderDelivered(new Date());
		Order updatedOrder = this.orderRepository.save(order);
		OrderDto orderDto = this.mapper.map(updatedOrder, OrderDto.class);
		
		return orderDto;
	}

	@Override
	public OrderResponse filterOrders(String filterBy) {
		List<Order> filteredOrders=new ArrayList<>();
		if(filterBy.equals("ALL")) {
			filteredOrders= this.orderRepository.findAll();
		}
		
		
		if(filterBy.equals("DATE")) {
			filteredOrders= this.orderRepository.findByOrderByOrderCreatedDesc();
		}
		
		if(filterBy.equals("NOT-PAID")) {
			filteredOrders= this.orderRepository.findByPaymentStatusStartsWith("Not PAID");
		}
		
		if(filterBy.equals("PAID")) {
			filteredOrders= this.orderRepository.findByPaymentStatusStartsWith("PAID");
		}
		
		 List<OrderDto> filteredOrdersDtos = filteredOrders.stream().map(order->this.mapper.map(order, OrderDto.class)).collect(Collectors.toList());
	     OrderResponse orders=new OrderResponse();
	     orders.setContent(filteredOrdersDtos);
		 return orders;
	}

	@Override
	public OrderResponse getOrdersByUser(int userId) {
		User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found"));
		List<Order> orders = this.orderRepository.findByUser(user);
		 List<OrderDto> OrdersDtos = orders.stream().map(order->this.mapper.map(order, OrderDto.class)).collect(Collectors.toList());
		 OrderResponse ordersResponse=new OrderResponse();
	     ordersResponse.setContent(OrdersDtos);
		 return ordersResponse;
	}
	
	
	
	
	
}
