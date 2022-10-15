package my.Ecom.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import my.Ecom.Models.Product;
import my.Ecom.Models.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	// email=username for our project
	public Optional<User> findByUserEmail(String userEmail);
//	public User findByEmail(String userEmail);
	
	List<User> findByUserNameContaining(String userName);
	
}
