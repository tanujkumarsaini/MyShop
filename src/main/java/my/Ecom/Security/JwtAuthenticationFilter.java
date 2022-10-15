package my.Ecom.Security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import my.Ecom.Repositories.UserRepository;
@Component
public class JwtAuthenticationFilter extends  OncePerRequestFilter{

	@Autowired
	private JwtHelper jwtHelper;
		
	@Autowired
	private UserDetailsService userDetailsService;
	
	Logger logger=LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//get the token from header
		//Authorization= Bearer jj4jlk4j.hfuh3.34343hj
		String requestToken = request.getHeader("Authorization");
		logger.info("message{}",requestToken);
		
		String username=null;
		String jwtToken=null;
		
		if(requestToken!=null&& requestToken.trim().startsWith("Bearer")){
			
			//get actual token
			jwtToken=requestToken.substring(7);
			
			//get the username from the token
		   try {
			   username=this.jwtHelper.getUsernameFromToken(jwtToken);
		   }
		   catch(ExpiredJwtException e) {
			   logger.info("Invalid token message{}","Jwt token expired");
		   }
			
		   catch(MalformedJwtException e) {
			   logger.info("Invalid token message{}","Invalid jwt token");
		   }
		   catch(IllegalArgumentException e) {
			   logger.info("Invalid token message{}","Unable to get token");
		   }
			
		   
		   //validate
		   
		   if(username!=null&& SecurityContextHolder.getContext().getAuthentication()==null) {
			   
			  UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			   
			  if(this.jwtHelper.validateToken(jwtToken, userDetails)) {
				  //set the authentication
				  UsernamePasswordAuthenticationToken auth=new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
				  auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				  
				  SecurityContextHolder.getContext().setAuthentication(auth);
				  
				  }else {
					  logger.info("Not validated message{}","Invalid jwt token");
					  
				  }
			  
			   
		   }else {
               logger.info("username message {} ", "username is null or auth is already there");
           }
		 
		   
		   }
		
		  else {
			   logger.info("token message{}","token does not start with bearer");
		   }
		   
		   filterChain.doFilter(request, response);
		
	}

}
