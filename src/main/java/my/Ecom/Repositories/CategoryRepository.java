package my.Ecom.Repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import my.Ecom.Models.Category;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
