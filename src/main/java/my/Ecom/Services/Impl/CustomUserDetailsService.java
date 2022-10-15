package my.Ecom.Services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import my.Ecom.Models.User;
import my.Ecom.Repositories.UserRepository;
@Service 
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		User user = this.userRepository.findByUserEmail(username).orElseThrow(()-> new UsernameNotFoundException("User not found with this given id"));
	    System.out.println("user loaded");
		return user;
	}

}
