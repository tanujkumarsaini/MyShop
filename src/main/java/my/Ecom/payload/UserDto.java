package my.Ecom.payload;



import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import CustomAnnotation.Phone;



public class UserDto {
	private int userId;

	@NotEmpty(message = "Must not be empty")
	@Size(min = 4,max=20,message = "User name should between 4 and 20")
	private String userName;

	@Email(message = "Enter correct email")
	private String userEmail;

    @Size(min = 5,max = 10,message = "Password should between 5 and 20")	
	private String userPassword;


   // @Pattern(regexp="(^$|[0-9]{10})")
    //@Digits(integer = 10,message ="Mobile number should be 10 digit", fraction = 0 )
   // @Range(min = 600000000,max = 9999999999,message = "mobile nomber should be 10 digit")
    
   @Phone
    private long userMobile;

    @Range(min = 100000,max = 999999 )
	private int userPincode;

	private String userAddress;
	
	@NotEmpty(message = "Select Gender")
	private String gender;

	private Date createAt;

	private boolean active;
	
	private Set<RoleDto> roles=new HashSet<>(); 

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public long getUserMobile() {
		System.out.println(UserDto.this.userMobile+"    getter");
		return userMobile;
	}

	public void setUserMobile(long userMobile) {
		
		this.userMobile = userMobile;
		System.out.println(UserDto.this.userMobile+"  setter");
	}

	public int getUserPincode() {
		return userPincode;
	}

	public void setUserPincode(int userPincode) {
		this.userPincode = userPincode;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	
	

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Set<RoleDto> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleDto> roles) {
		this.roles = roles;
	}
	
	
	
	

}
