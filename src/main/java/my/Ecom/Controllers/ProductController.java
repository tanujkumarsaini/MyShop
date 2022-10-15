package my.Ecom.Controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.multipart.MultipartFile;


import my.Ecom.Models.Product;
import my.Ecom.Services.FileUploadService;
import my.Ecom.Services.ProductService;
import my.Ecom.config.AppConstants;
import my.Ecom.payload.ApiResponse;
import my.Ecom.payload.ProductDto;
import my.Ecom.payload.ProductResponse;
import my.Ecom.payload.ProductSearchResponse;
/**
 * This class contains all the urls to Create,Update,Fetch and Delete Products;
 * @since 1.0
 * @author admin
 *
 */
@RestController
public class ProductController {
@Autowired
private ProductService productService;

@Autowired
private FileUploadService fileUploadService;

@Value("${product.images.path}")
private String imagePath;

@PostMapping("/products/images/{productId}")
public ResponseEntity<?> uploadImageOfProduct(
		@PathVariable int productId,
		@RequestParam("product_image") MultipartFile file
		){
	
	ProductDto product=this.productService.getProduct(productId);
	String imageName;
	try {
		imageName = this.fileUploadService.uploadFile(imagePath, file);
		product.setImageName(imageName);
		ProductDto productDto = this.productService.updateProduct(product, productId);
		return new ResponseEntity<>(productDto,HttpStatus.OK);
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		Map<String,Object> data=new HashMap<>();
		data.put("message", "File not uploaded on server");
		return new ResponseEntity<>(data,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
}

/**
 * This method provide url to create product
 * @since 1.0
 * @param product
 * @return Product
 * @see first.Rest.Services.ProductServiceImpl
 */

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/categories/{categoryId}/products")
	public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto,@PathVariable int categoryId) {
  		ProductDto createdProduct=productService.createProduct(productDto,categoryId);
		return new ResponseEntity<ProductDto>(createdProduct,HttpStatus.OK);
		
    }
	
    /**
     * This method provide url to get all product
     * @since 1.0
     * @return List of Product
     * @see first.Rest.Services.ProductServiceImpl
     */

// @RequestMapping(value = "/all-product",method = RequestMethod.GET)
    @GetMapping("/products")
    public ProductResponse getAllProduct(
    		@RequestParam(value = "pageNumber",required = false,defaultValue = AppConstants.PAGE_NUMBER_STRING) int pageNumber,
    		@RequestParam(value = "pageSize",required = false,defaultValue = AppConstants.PAGE_SIZE__STRING) int pageSize,
    		@RequestParam(value="sortBy",required=false,defaultValue = AppConstants.SORT_BY_STRING) String sortBy,
    		@RequestParam(value = "sortDir",required = false, defaultValue =  AppConstants.SORT_DIR_STRING) String sortDir
    		){
    	ProductResponse allProduct = productService.getAllProduct(pageNumber, pageSize,sortBy,sortDir);
		return allProduct;
	}
	
    /**
     * This method provide url to get single product to given product id
     * @since 1.0
     * @param productId
     * @return Product
     * @see first.Rest.Services.ProductServiceImpl
     */
@GetMapping("/products/{productId}")
 public ResponseEntity<ProductDto> getProduct(@PathVariable int productId) {
  ProductDto productDto=productService.getProduct(productId);
  return new ResponseEntity<ProductDto>(productDto,HttpStatus.OK);
	 
 }



/**
 * This method provide url to delete product 
 * @since 1.0
 * @param productId
 * @return message in the form of String
 * @see first.Rest.Services.ProductServiceImpl
 */

@DeleteMapping("/products/{productId}")  
public ResponseEntity<ApiResponse> deleteProduct(@PathVariable int productId) {
	productService.deleteProduct(productId);
	return new ResponseEntity<ApiResponse>(new ApiResponse("product deleted successfully",true),HttpStatus.OK);
	
}
 
/**
 * This method provide url to update product
 * @since 1.0
 * @param productId
 * @param Product
 * @return Product
 * @see first.Rest.Services.ProductServiceImpl
 */

@PutMapping("/products/{productId}") 
public ProductDto updateProduct(@PathVariable("productId") int pid,@RequestBody ProductDto newProduct) {
	ProductDto updatedProduct=productService.updateProduct(newProduct, pid);
	return updatedProduct;
}



@GetMapping("/categories/{categoryId}/products")
public ResponseEntity<ProductResponse>  getProductByCategory(@PathVariable int categoryId,
		@RequestParam(value = "pageNumber",required   = false,defaultValue = "0") int pageNumber,
		@RequestParam(value = "pageSize",required = false,defaultValue = "5") int pageSize
		) {
	ProductResponse productByCategory = productService.getProductByCategory(categoryId,pageNumber,pageSize);
	return new ResponseEntity<ProductResponse>(productByCategory,HttpStatus.OK);
	
}


//seerch product by name
@GetMapping("/products/search/{searchData}")
public ResponseEntity<ProductSearchResponse> searchProductsByName(
		@PathVariable String searchData
		){
	System.out.println("Search"+searchData);
	ProductSearchResponse productBySearch = productService.searchProductByName(searchData);
	return new ResponseEntity<ProductSearchResponse>(productBySearch,HttpStatus.OK);
}



//get the image of given product
@GetMapping("/products/images/{productId}")
public void downloadImage(@PathVariable int productId,HttpServletResponse response) throws IOException{
	ProductDto product=this.productService.getProduct(productId);
	String imageName=product.getImageName();
	String fullPath=imagePath+File.separator+imageName;
	System.out.println(fullPath);
	InputStream resource=this.fileUploadService.getResource(fullPath);
	response.setContentType(MediaType.IMAGE_JPEG_VALUE);
	OutputStream outputStream=response.getOutputStream();
	StreamUtils.copy(resource, outputStream);
}
	




}
