package my.Ecom.Controllers;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import my.Ecom.Services.OrderService;
import my.Ecom.payload.OrderDto;
import my.Ecom.payload.PaymentOrderResponse;
import my.Ecom.payload.PaymentSuccessResponse;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	
	@Autowired
	private OrderService orderService;
	
	//create order 
	@PostMapping("/create")
	public PaymentOrderResponse createOrder(
			@RequestParam("price") int price
			) throws RazorpayException {
		
		RazorpayClient client=new RazorpayClient("rzp_test_EGsQvPvh17P2vy","KtDFM0iMcWJ5aS9tvzIPpGgH");
		JSONObject option=new JSONObject();
		option.put("amount", price*100);
		option.put("currency", "INR");
		//option.put("reciept", "rept12345");
		
		
		Order order=client.orders.create(option);
		System.out.println(order);
		//save the order id and infromation too datbase
		
		PaymentOrderResponse porder=new PaymentOrderResponse();
		porder.setMessage("CREATED");
		porder.setPrice(order.get("amount") + "");
		porder.setOrderId(order.get("id"));
		porder.setOrderInformation("Order is just created from razorpay server !!");
		
		return porder;
	}
	
	//capture payment method
	@PostMapping("/success")
	public PaymentSuccessResponse capturePayment(
			@RequestParam("razorpay_payment_id") String razorpay_payment_id,
			@RequestParam("razorpay_order_id") String razorpay_order_id,
			@RequestParam("razorpay_signature") String razorpay_signature,
			@RequestParam("user_order_id") int user_order_id
			) {
		//update the order=> change to order status to success...
		OrderDto dto=new OrderDto();
		dto.setPaymentStatus("PAID");
		this.orderService.updateOrder(dto, user_order_id);
		PaymentSuccessResponse psuccess=new PaymentSuccessResponse();
		psuccess.setCaptured(true);
		psuccess.setUser_order_id(user_order_id+"");
		psuccess.setPaymentStatus("PAID");
		
		
		return psuccess;
		
		
	}

}
