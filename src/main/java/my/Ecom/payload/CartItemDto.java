package my.Ecom.payload;

import javax.persistence.OneToOne;

import my.Ecom.Models.Product;

public class CartItemDto implements Comparable<CartItemDto>{

private int cartItemId;

private ProductDto product;

private int quantity;

private double totalProductPrice;

public int getCartItemId() {
	return cartItemId;
}

public void setCartItemId(int cartItemId) {
	this.cartItemId = cartItemId;
}

public ProductDto getProduct() {
	return product;
}

public void setProduct(ProductDto product) {
	this.product = product;
}

public int getQuantity() {
	return quantity;
}

public void setQuantity(int quantity) {
	this.quantity = quantity;
}

public double getTotalProductPrice() {
	return totalProductPrice;
}

public void setTotalProductPrice(double totalProductPrice) {
	this.totalProductPrice = totalProductPrice;
}

@Override
public int compareTo(CartItemDto o) {
	
	return this.getCartItemId()-o.getCartItemId();
}




}
