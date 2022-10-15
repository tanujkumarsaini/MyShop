package my.Ecom.Controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import my.Ecom.Models.User;
import my.Ecom.Services.CartService;
import my.Ecom.payload.CartDto;
import my.Ecom.payload.ItemRequest;

@RestController

public class CartController {

	
	
	@Autowired
	private CartService cartService;
	
	@PostMapping("/carts/")
   public ResponseEntity<CartDto> addItemToCart(@RequestBody ItemRequest request,Principal principal){
		//        authentication:dynamic
		System.out.println(principal.getName()+" from token"); 
	
		CartDto cartDto=this.cartService.addItem(request, principal.getName());
	
		return new ResponseEntity<CartDto>(cartDto,HttpStatus.OK);
	}
	
	//get cart
	@GetMapping("/carts/")
	public ResponseEntity<CartDto> getCart(Principal principal){
		CartDto cartDto=this.cartService.get(principal.getName());
		return new ResponseEntity<CartDto>(cartDto,HttpStatus.OK);
		
	}
	
	@PutMapping("/carts/{productId}"
			+ ""
			+ ""
			+ "")
	public ResponseEntity<CartDto> removeProductFromCart(@PathVariable int productId,Principal principal){
		CartDto cartDto=this.cartService.removeIte(principal.getName(), productId);
		return new ResponseEntity<CartDto>(cartDto,HttpStatus.OK);
	}
	
}
