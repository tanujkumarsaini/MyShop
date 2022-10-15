package my.Ecom.Services;

import java.util.List;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import my.Ecom.Models.Product;
import my.Ecom.payload.ProductDto;
import my.Ecom.payload.ProductResponse;
import my.Ecom.payload.ProductSearchResponse;
/**
 * This interface provides methods to perform some operations like Create,Update,Fetch and Delete Products
 * @since 10
 * @see first.Rest.Services.ProductServiceImpl
 */
public interface ProductService {
	/**
	 * This method create a new product
	 * @since 1.0
	 * @param product
	 * @return Product
	 * @see first.Rest.Services.ProductServiceImpl
	 */
	public ProductDto createProduct(ProductDto product,int categoryId);
	
	/**
	 * This method returns all products
	 * @since 1.0
	 * @param nothing
	 * @return List of Product
	 * @see first.Rest.Services.ProductServiceImpl
	 */
	public  ProductResponse getAllProduct(int pageNumber,int pageSize,String sortBy,String sortDir);
	
	

	/**
	 * This method returns a single product by given id
	 * @since 1.0
	 * @param product id
	 * @return single Product
	 * @see first.Rest.Services.ProductServiceImpl
	 */
	 public ProductDto getProduct(int productId);
	 
	 /**
		 * This method can delete product by given product id
		 * @since 1.0
		 * @param product id
		 * @return nothing
		 * @see first.Rest.Services.ProductServiceImpl
		 */
	 public void deleteProduct(int productId);
	 /**
		 * This method update the product
		 * @since 1.0
		 * @param product id
		 * @param new Product
		 * @return Product
		 * @see first.Rest.Services.ProductServiceImpl
		 */
	 public ProductDto updateProduct(ProductDto newProduct,int productId);
	 
	 
	 
	 public ProductResponse getProductByCategory(int categoryId,int pageNumber,int pageSize);
	 
	 
	 public ProductSearchResponse searchProductByName(String search);
}
