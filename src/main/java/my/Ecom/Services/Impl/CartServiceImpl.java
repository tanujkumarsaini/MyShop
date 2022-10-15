package my.Ecom.Services.Impl;

import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.Ecom.Exception.ResourceNotFoundException;
import my.Ecom.Models.Cart;
import my.Ecom.Models.CartItem;
import my.Ecom.Models.Product;
import my.Ecom.Models.User;
import my.Ecom.Repositories.CartRepository;
import my.Ecom.Repositories.ProductRepository;
import my.Ecom.Repositories.UserRepository;
import my.Ecom.Services.CartService;
import my.Ecom.payload.CartDto;
import my.Ecom.payload.ItemRequest;
import my.Ecom.payload.UserDto;

@Service
public class CartServiceImpl implements CartService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	
	@Autowired
	private ModelMapper mapper;
	
	
	@Override
	public CartDto addItem(ItemRequest item, String userName) {
		int productId=item.getProductId();
		int quantity=item.getQuantity();
		
		if(quantity<=0) {
			throw new ResourceNotFoundException("Invalid Quantity !!");
		}
		
		//get the user
		System.out.println("fetching user"+userName);
		//User user=this.userRepository.findByUserEmail(userName).orElseThrow(()-> new ResourceNotFoundException());
	
		User user = userRepository.findByUserEmail(userName).orElseThrow(()->new ResourceNotFoundException("User not find with this email!!!!    "+userName));
		
		System.out.println(user);
		//get the product from db :productRepository
		
		Product product = this.productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product not found!!"));
		if(!product.isStock()) {
			throw new ResourceNotFoundException("Product is out of stock");
		}
		System.out.println(product.getProductPrice());
		
		//create new cartItem : with product and quantity
		
		CartItem cartItem=new CartItem();
		cartItem.setProduct(product);
		cartItem.setQuantity(quantity);
		cartItem.setTotalProductPrice();
		
		//getting cart from user
		Cart cart=user.getCart();
		
		//if cart is null that means user does not have any cart
		if(cart==null) {
			//we will create new caart
			cart=new Cart();
			cart.setUser(user);
		}
		
		//add items mein cart ko
		
		Set<CartItem> items=cart.getItems();
		AtomicReference<Boolean> flag=new AtomicReference<>(false);
		Set<CartItem> newItems=items.stream().map((i)->{
			//changes
			if(i.getProduct().getProductId()==product.getProductId()) {
				i.setQuantity(quantity);
				i.setTotalProductPrice();
				flag.set(true);
			}
			return i;
			
		}).collect(Collectors.toSet());
		
		//check
		if(flag.get()) {
			//newItems ko item ki jagah set karenge
			items.clear();
			items.addAll(newItems);
		}
		else {
			cartItem.setCart(cart);
			items.add(cartItem);
		}
		
		Cart updatedCart=this.cartRepository.save(cart);
		return this.mapper.map(updatedCart, CartDto.class);
		
		
	}

	@Override
	public CartDto get(String userName) {
	User user = this.userRepository.findByUserEmail(userName).orElseThrow(()-> new ResourceNotFoundException("User not found with this email!!  "+userName));
	Cart cart = this.cartRepository.findByUser(user).orElseThrow(()-> new ResourceNotFoundException("Cart not found!!"));
	return this.mapper.map(cart, CartDto.class);
		
	}

	@Override
	public CartDto removeIte(String userName, int productId) {
		User user=this.userRepository.findByUserEmail(userName).orElseThrow(()-> new ResourceNotFoundException("User not found with given userName!!"));
		Cart cart=user.getCart();
		Set<CartItem> items=cart.getItems();
		boolean result=items.removeIf((item)->item.getProduct().getProductId()==productId);
		Cart updatedCart=cartRepository.save(cart);
		
		return this.mapper.map(updatedCart, CartDto.class);
	}

}
