package my.Ecom.Services;

import org.springframework.stereotype.Service;

import my.Ecom.payload.CartDto;
import my.Ecom.payload.ItemRequest;


public interface CartService {

	//add item to cart
	// we will check the availability of cart if cart is available then we will add item to cart otherwise we will create a new cart and add the item to it
	CartDto addItem(ItemRequest item, String userName);
	
	//get cart of user 
	CartDto get(String userName);
	
	//remove item from cart
	CartDto removeIte(String userName,int itemId);
}
