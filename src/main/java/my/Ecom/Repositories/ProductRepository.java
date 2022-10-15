package my.Ecom.Repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import my.Ecom.Models.Category;
import my.Ecom.Models.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

Page<Product> findByCategory(Category category,Pageable pageable);

List<Product> findByProductNameContaining(String productName);


}
