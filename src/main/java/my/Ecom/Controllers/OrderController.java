package my.Ecom.Controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import my.Ecom.Repositories.OrderRepository;
import my.Ecom.Services.OrderService;
import my.Ecom.payload.ApiResponse;
import my.Ecom.payload.OrderDto;
import my.Ecom.payload.OrderRequest;
import my.Ecom.payload.OrderResponse;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	
	
	@PostMapping("/")
	public ResponseEntity<OrderDto> createOrder(@RequestBody OrderRequest request,Principal principal){
		
		OrderDto createdOrder = this.orderService.createOrder(request,principal.getName());
		
		return new ResponseEntity<OrderDto>(createdOrder,HttpStatus.CREATED);
		
	}
	
	
	@GetMapping("/")
	public ResponseEntity<OrderResponse> getAll(
			
		  @RequestParam(value ="pageNumber",required = false,defaultValue ="0") int pageNumber,
		  @RequestParam(value ="pageSize",required = false,defaultValue = "100") int pageSize,
		  @RequestParam(value ="sortBy",required = false,defaultValue = "orderId") String sortBy,
		  @RequestParam(value ="sortDir", required = false,defaultValue = "asc") String sortDir
			
			){
		
		OrderResponse all = this.orderService.getAll(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<OrderResponse>(all,HttpStatus.OK);
	}
	
	
	@GetMapping("/{orderId}")
	public ResponseEntity<OrderDto> getById(@PathVariable int orderId){
		OrderDto orderDto = this.orderService.get(orderId);
		return new ResponseEntity<OrderDto>(orderDto,HttpStatus.OK) ;
	}
	

	@DeleteMapping("/{orderId}")
	public ResponseEntity<ApiResponse> deleteOrder(@PathVariable int orderId){
		 this.orderService.deleteOrder(orderId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Order Deleted",true),HttpStatus.OK) ;
	}
	
	
	
	
	@PutMapping("/{orderId}")
	public ResponseEntity<OrderDto> updateOrder(@PathVariable int orderId,@RequestBody OrderDto orderDto){
		OrderDto updatedOrder = this.orderService.updateOrder(orderDto, orderId);
		return new ResponseEntity<OrderDto>(updatedOrder,HttpStatus.OK);
	}
	
	@PutMapping("/status/{orderId}")
	public ResponseEntity<OrderDto> updateOrderStatus(@PathVariable int orderId){
		OrderDto updatedOrder = this.orderService.updateOrderStatus(orderId);
		return new ResponseEntity<OrderDto>(updatedOrder,HttpStatus.OK);
	}
	
	@GetMapping("/filter/{filterBy}")
	public ResponseEntity<OrderResponse> getAllFilteredOrders( @PathVariable String filterBy){		
		OrderResponse filterOrders = this.orderService.filterOrders(filterBy);
		return new ResponseEntity<OrderResponse>(filterOrders,HttpStatus.OK);
	}
	
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<OrderResponse> getAllOrdersByUser( @PathVariable int userId){		
		OrderResponse filterOrders = this.orderService.getOrdersByUser(userId);
		return new ResponseEntity<OrderResponse>(filterOrders,HttpStatus.OK);
	}
	
}
