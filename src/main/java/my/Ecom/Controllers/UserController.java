package my.Ecom.Controllers;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import my.Ecom.Models.*;
import my.Ecom.Services.UserService;
import my.Ecom.payload.ApiResponse;
import my.Ecom.payload.RoleDto;
import my.Ecom.payload.UserDto;

@RestController
@CrossOrigin
public class UserController {
@Autowired
private UserService userService;

@Autowired
private PasswordEncoder passwordEncoder;

@Autowired
private ModelMapper mapper;

@PostMapping("/users")	
public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto) {

String userEmail = userDto.getUserEmail();

if(this.userService.checkUserFromDB(userEmail)) {
	userDto.setActive(true);
	userDto.setCreateAt(new Date());
	userDto.setUserPassword(this.passwordEncoder.encode(userDto.getUserPassword()));



	UserDto createdUser = userService.createUser(userDto);
	return new ResponseEntity<UserDto>(createdUser,HttpStatus.CREATED);

}
else {

return new ResponseEntity<ApiResponse>(new ApiResponse("User is already created",false),HttpStatus.BAD_REQUEST);
}
}

@GetMapping("/users/{userId}")
private ResponseEntity<UserDto> getUser(@PathVariable int userId) {
	UserDto user = userService.getUser(userId);
	return new ResponseEntity<UserDto>(user,HttpStatus.OK);
}

@GetMapping("/users")
private ResponseEntity<List<UserDto>> getAllUsers(){
List<UserDto> allUser = userService.getAllUser();
return new ResponseEntity<List<UserDto>>(allUser,HttpStatus.OK);
}

@DeleteMapping("/users/{userId}")
private ResponseEntity<ApiResponse> deleteUser(@PathVariable  int userId) {
userService.deleteUser(userId);
return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted seccessfully",true),HttpStatus.OK);
}

@PutMapping("/users/{userId}")
private ResponseEntity<UserDto> updateUser(@RequestBody UserDto newUser,@PathVariable int userId) {
UserDto updatedUser = userService.updateUser(newUser, userId);
return new ResponseEntity<UserDto>(updatedUser,HttpStatus.OK);
}


@GetMapping("/users/email/{userEmail}")
private ResponseEntity<UserDto> getUserByEmail(@PathVariable String userEmail) {
UserDto byEmail = userService.getByEmail(userEmail);
return new ResponseEntity<UserDto>(byEmail,HttpStatus.OK);
}


@PutMapping("/users/role/staff/{email}")
private ResponseEntity<UserDto> updateUserRoles(@PathVariable String email) {
UserDto updatedUser = userService.addToStaff(email);
return new ResponseEntity<UserDto>(updatedUser,HttpStatus.OK);
}


@GetMapping("/users/search/{searchData}")
private ResponseEntity<List<UserDto>> getAllSearchedUsers(
		@PathVariable String searchData
		){
List<UserDto> searchedUsers = this.userService.searchedUsers(searchData);



return new ResponseEntity<List<UserDto>>(searchedUsers,HttpStatus.OK);
}

}
