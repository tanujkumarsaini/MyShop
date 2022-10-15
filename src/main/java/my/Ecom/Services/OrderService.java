package my.Ecom.Services;

import java.util.List;

import my.Ecom.payload.OrderDto;
import my.Ecom.payload.OrderRequest;
import my.Ecom.payload.OrderResponse;

public interface OrderService {

	//create order
	OrderDto createOrder(OrderRequest request,String username);
	
	//update order
	OrderDto updateOrder(OrderDto orderDto,int orderId);
	
	//delete order
	void deleteOrder(int orderId);
	
	//get all orders
	OrderResponse getAll(int pageNumber,int pageSize,String sortBy,String sortDir);
	
	//get single order
	OrderDto get(int orderId);
	
	//update order status
	OrderDto updateOrderStatus(int orderid);
	
	OrderResponse filterOrders(String filterBy);
	
	OrderResponse getOrdersByUser(int userId);
}
