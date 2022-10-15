package my.Ecom.Services.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import my.Ecom.Exception.ResourceNotFoundException;
import my.Ecom.Models.Category;
import my.Ecom.Models.Product;
import my.Ecom.Repositories.CategoryRepository;
import my.Ecom.Repositories.ProductRepository;
import my.Ecom.Services.ProductService;
import my.Ecom.payload.ProductDto;
import my.Ecom.payload.ProductResponse;
import my.Ecom.payload.ProductSearchResponse;

@Service
public class ProductServiceImpl implements ProductService{
@Autowired
private ProductRepository productRepository;

@Autowired
private ModelMapper mapper;

@Autowired
private CategoryRepository catRepo;

@Override
public ProductDto createProduct(ProductDto productDto,int categoryId) {
	Category category = this.catRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Given category is not found"));
	Product product = this.mapper.map(productDto, Product.class);
	product.setCategory(category);
	Product savedProduct = this.productRepository.save(product);
	return this.mapper.map(savedProduct, ProductDto.class);
}

@Override
public ProductResponse getAllProduct(int pageNumber,int pageSize,String sortBy,String sortDir) {
	Sort sort=null;
	if(sortDir.trim().toLowerCase().equals("asc")) {
	sort=Sort.by(sortBy).ascending();
	}
	else {
		sort=Sort.by(sortBy).descending();
	}
	
	Pageable pageable=PageRequest.of(pageNumber,pageSize,sort);
	Page<Product> page = this.productRepository.findAll(pageable);
	List<Product> allProduct  = page.getContent();
	
	List<ProductDto> dtos = allProduct.stream().map(product->this.mapper.map(product, ProductDto.class)).collect(Collectors.toList());
	
	

	ProductResponse response=new ProductResponse();
	response.setContent(dtos);
	response.setPageNumber(page.getNumber());
	response.setPageSize(page.getSize());
	response.setTotalPages(page.getTotalPages());
	response.setTotalElements(page.getTotalElements());
	response.setLastPage(page.isLast());
	
	
	return response ;

}

@Override
public ProductDto getProduct(int productId) {
    Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product with "+productId+" id not found on server"));
   return mapper.map(product, ProductDto.class);
    
}

@Override
public void deleteProduct(int productId) {
	Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product with "+productId+" id not found on server"));
	this.productRepository.delete(product);
	
}

@Override
public ProductDto updateProduct(ProductDto newProduct, int productId) {
	Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product with "+productId+" id not found on server"));
	
	int categoryId = newProduct.getCategory().getCategoryId();
	Category category = this.catRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category not found"));
	
	product.setProductName(newProduct.getProductName());
	product.setProductDesc(newProduct.getProductDesc());
	product.setProductPrice(newProduct.getProductPrice());
	product.setLive(newProduct.isLive());
	product.setImageName(newProduct.getImageName());
	product.setStock(newProduct.isStock());
	product.setProductQuantity(newProduct.getProductQuantity());
	product.setCategory(category);
	
	Product savedProduct = productRepository.save(product);
	
	return this.mapper.map(savedProduct, ProductDto.class);
}

@Override
public ProductResponse getProductByCategory(int categoryId,int pageNumber,int pageSize) {
	Category category = this.catRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Given category is not found"));
	Pageable pageable=PageRequest.of(pageNumber, pageSize);
	
	
	Page<Product> page = this.productRepository.findByCategory(category,pageable);
	List<Product> findByCategory = page.getContent();
	
	
	List<ProductDto> dtos = findByCategory.stream().map((product)->this.mapper.map(product,ProductDto.class )).collect(Collectors.toList());
	
	ProductResponse response=new ProductResponse();
	response.setContent(dtos);
	response.setPageNumber(page.getNumber());
	response.setPageSize(page.getSize());
	response.setTotalPages(page.getTotalPages());
	response.setTotalElements(page.getTotalElements());
	response.setLastPage(page.isLast());
	
	
	return response ;
}

@Override
public ProductSearchResponse searchProductByName(String search) {
	
	List<Product> products = this.productRepository.findByProductNameContaining(search);
	 List<ProductDto> productDtos = products.stream().map((product)->this.mapper.map(product, ProductDto.class)).collect(Collectors.toList());

	 ProductSearchResponse response=new ProductSearchResponse();
		response.setContent(productDtos);
	
	return response;
}





	

}
