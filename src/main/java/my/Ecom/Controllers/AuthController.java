package my.Ecom.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import my.Ecom.Exception.BadUserLoginDetailsException;
import my.Ecom.Security.JwtHelper;
import my.Ecom.payload.JwtRequest;
import my.Ecom.payload.JwtResponse;
import my.Ecom.payload.UserDto;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private UserDetailsService userDetailService;
	
	@Autowired
	private JwtHelper jwtHelper;

	@Autowired
	private ModelMapper mapper;
	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) throws Exception{
		
		//authenticate
		this.authenticateUser(request.getUsername(),request.getPassword());
		UserDetails userDetails=this.userDetailService.loadUserByUsername(request.getUsername());
		String token = this.jwtHelper.generateToken(userDetails);
		
		JwtResponse response=new JwtResponse();
		response.setToken(token);
		response.setUser(this.mapper.map(userDetails, UserDto.class));
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

	private void authenticateUser(String username, String password) throws Exception {
		try {
		//authenticate
		manager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		}
		catch(BadCredentialsException e) {
			throw new BadUserLoginDetailsException("Invalid username or password!!");
		}
		catch(DisabledException e) {
			throw new BadUserLoginDetailsException("user is not active!!");
			
		}
	}
	
}
