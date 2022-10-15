package my.Ecom.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * This is  a model class to store and fetch the data of a product
 * @since 1.0
 * @author admin
 *
 */
@Entity
public class Product {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int productId;

@Column(length = 300,unique = true)
private String productName;

private String productDesc;

private double productPrice;

private boolean live;

private boolean stock;

private String imageName;

@ManyToOne(fetch = FetchType.EAGER)
private Category category; 

private int productQuantity;

public Product() {
	super();
	// TODO Auto-generated constructor stub
}



public Product(int productId, String productName, String productDesc, double productPrice, boolean live, boolean stock,
		String imageName) {
	super();
	this.productId = productId;
	this.productName = productName;
	this.productDesc = productDesc;
	this.productPrice = productPrice;
	this.live = live;
	this.stock = stock;
	this.imageName = imageName;
}



public int getProductId() {
	return productId;
}
public void setProductId(int productId) {
	this.productId = productId;
}
public String getProductName() {
	return productName;
}
public void setProductName(String productName) {
	this.productName = productName;
}
public String getProductDesc() {
	return productDesc;
}
public void setProductDesc(String productDesc) {
	this.productDesc = productDesc;
}
public double getProductPrice() {
	return productPrice;
}
public void setProductPrice(double productPrice) {
	this.productPrice = productPrice;
}
public boolean isStock() {
	return stock;
}
public void setStock(boolean stock) {
	this.stock = stock;
}


public boolean isLive() {
	return live;
}
public void setLive(boolean live) {
	this.live = live;
}



public String getImageName() {
	return imageName;
}
public void setImageName(String imageName) {
	this.imageName = imageName;
}



public Category getCategory() {
	return category;
}



public void setCategory(Category category) {
	this.category = category;
}



public int getProductQuantity() {
	return productQuantity;
}



public void setProductQuantity(int productQuantity) {
	this.productQuantity = productQuantity;
}




}
