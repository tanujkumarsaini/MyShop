package my.Ecom.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import my.Ecom.Models.Cart;
import my.Ecom.Models.User;
@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>{

	
	Optional<Cart> findByUser(User user);
	
}
