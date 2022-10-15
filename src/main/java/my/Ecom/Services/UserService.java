package my.Ecom.Services;

import java.util.List;

import my.Ecom.Models.User;
import my.Ecom.payload.UserDto;

public interface UserService {
	//create
	public UserDto createUser(UserDto userDto); 
	
	//get by id
	public UserDto getUser(int userId);
	
	//get all users
	public List<UserDto> getAllUser();
	
	//delete
	public void deleteUser(int userId);
	
	//update
	public UserDto updateUser(UserDto userDto,int userId);
	
    //get by email
	public UserDto getByEmail(String email);
	
	public boolean checkUserFromDB(String email);
	
	public UserDto addToStaff(String email);
	
	public List<UserDto> searchedUsers(String data);

}
