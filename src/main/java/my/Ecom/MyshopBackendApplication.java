package my.Ecom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import my.Ecom.Models.Order;
import my.Ecom.Models.Role;
import my.Ecom.Models.User;
import my.Ecom.Repositories.OrderRepository;
import my.Ecom.Repositories.RoleRepository;
import my.Ecom.Repositories.UserRepository;

@SpringBootApplication
public class MyshopBackendApplication implements CommandLineRunner {
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(MyshopBackendApplication.class, args);
	}
	
	@Bean
	public ModelMapper mapper() {
		return new ModelMapper();
	}
	
	

	@Override
	public void run(String... args) throws Exception {
		
		
		
		try {
			Role role1=new Role();
			role1.setId(255);
			role1.setName("ROLE_ADMIN");
			
			Role role2=new Role();
			role2.setId(355);
			role2.setName("ROLE_NORMAL");
			
			Role role3=new Role();
			role3.setId(155);
			role3.setName("ROLE_STAFF");
			
			List<Role> roles=new ArrayList<>();
			roles.add(role3);
			roles.add(role2);
			roles.add( role1);
			
			
			
			roleRepository.saveAll(roles);
		//	roleRepository.saveAll(List.of(role1,role2,role3));
			
			
			User user=new User();
			user.setUserName("Tanuj");
			user.setUserEmail("tanujsaini46@gmail.com");
			user.setUserPassword(this.passwordEncoder.encode("Tanuj@"));
			user.setUserAddress("Bijnor");
			user.setUserMobile(123456789);
			user.setUserPincode(246701);
			user.setGender("MALE");
			user.setActive(true); 
		    user.setCreateAt(new Date());
			user.setRoles(new HashSet<>(roles));
			
			//create user with admin role and insert them
			try {
				User user1=this.userRepository.findByUserEmail("tanujsaini46@gmail.com").get();
				
			}catch(Exception e) {
				System.out.println("saving admin user");
				this.userRepository.save(user);
			}
			
		}
		catch(Exception e) {
			System.out.println("User already there!!");
			e.printStackTrace();
		}
		
	} 


}
