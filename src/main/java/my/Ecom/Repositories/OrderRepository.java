package my.Ecom.Repositories;



import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import my.Ecom.Models.Order;
import my.Ecom.Models.User;
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{

	public List<Order> findByUser(User user);
	
	public List<Order> findByOrderByOrderCreatedDesc();

	public List<Order> findByPaymentStatusStartsWith(String paymentStatus);
	
	
	
}
