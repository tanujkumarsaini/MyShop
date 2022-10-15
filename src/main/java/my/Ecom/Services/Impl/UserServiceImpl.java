package my.Ecom.Services.Impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.Ecom.Exception.ResourceNotFoundException;
import my.Ecom.Models.Role;
import my.Ecom.Models.User;
import my.Ecom.Repositories.RoleRepository;
import my.Ecom.Repositories.UserRepository;
import my.Ecom.Services.UserService;
import my.Ecom.payload.ProductDto;
import my.Ecom.payload.UserDto;

@Service
public class UserServiceImpl implements UserService{
@Autowired
private UserRepository userRepository;

@Autowired
private RoleRepository roleRepository;

@Override
public UserDto createUser(UserDto userDto) {
	User user=toEntity(userDto);
	
	//get the normal user role
	Role role=this.roleRepository.findById(355).get();
	user.getRoles().add(role);
	
	
	User createdUser=userRepository.save(user);
	System.out.println("without  dto----------- "+createdUser.getRoles());
	
	
	UserDto userDto2=this.toDto(createdUser);
	System.out.println("Dto------------- "+userDto2.getRoles());
	return userDto2;
}

@Override 
public UserDto getUser(int userId) {
   User user=userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found"+userId));
   
	return this.toDto(user);
}

@Override
public List<UserDto> getAllUser() {

	List<User> allUser = userRepository.findAll();
	List<UserDto> allDtos = allUser.stream().map(user->this.toDto(user)).collect(Collectors.toList());
	return allDtos;
}

@Override
public void deleteUser(int userId) {
	User user=userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found"+userId));
    userRepository.delete(user);
	
}

@Override
public UserDto updateUser(UserDto t, int userId) {
	User u=userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found"+userId));
	//u.setUserId(t.getUserId());
	u.setUserName(t.getUserName());
    u.setUserAddress(t.getUserAddress());
    u.setUserMobile(t.getUserMobile());
    u.setUserPincode(t.getUserPincode());
   u.setGender(t.getGender());
        
    User user=userRepository.save(u);
   
	return this.toDto(user);
}

@Override
public UserDto getByEmail(String email) {
	User user = userRepository.findByUserEmail(email).orElseThrow(()->new ResourceNotFoundException("User not find with this email!!    "+email));
	return this.toDto(user);
}
@Autowired
private ModelMapper mapper;

public UserDto toDto(User u) {
	/*
	UserDto dto=new UserDto();
	dto.setUserId(u.getUserId());
	dto.setUserName(u.getUserName());
	dto.setUserEmail(u.getUserEmail());
	dto.setUserPassword(u.getUserPassword());
	dto.setUserAddress(u.getUserAddress());
	dto.setUserMobile(u.getUserMobile());
	dto.setActive(u.isActive());
	dto.setCreateAt(u.getCreateAt());
	dto.setUserPincode(u.getUserPincode());
	//dto.getRoles(u.getRole());
	 */
	
	return this.mapper.map(u, UserDto.class);
	
}

public User toEntity(UserDto t) {
	/*
	User u=new User();
	u.setUserId(t.getUserId());
	u.setUserName(t.getUserName());
    u.setUserEmail(t.getUserEmail());
    u.setUserPassword(t.getUserPassword());
    u.setUserAddress(t.getUserAddress());
    u.setUserMobile(t.getUserMobile());
    u.setUserPincode(t.getUserPincode());
    u.setActive(t.isActive());
    u.setCreateAt(t.getCreateAt());
*/
	
    return this.mapper.map(t, User.class);
    
}

@Override
public boolean checkUserFromDB(String email) {
	User user = userRepository.findByUserEmail(email).orElse(null);
   if(user==null) {
	return true;
   }
   else {
	   return false;
   }
}

@Override
public UserDto addToStaff(String email) {
	User user = userRepository.findByUserEmail(email).orElse(null);
	Set<Role> roles = user.getRoles();
	Role newRole = this.roleRepository.findById(155).orElse(null);
	
	roles.add(newRole);
	
	User savedUser = this.userRepository.save(user);
	
	return this.mapper.map(savedUser, UserDto.class);
}

@Override
public List<UserDto> searchedUsers(String data) {
	List<User> searchedUsers = this.userRepository.findByUserNameContaining(data);
	List<UserDto> searchedUsersDto = searchedUsers.stream().map((u)->this.mapper.map(u, UserDto.class)).collect(Collectors.toList());
    return searchedUsersDto;
	
}



}
