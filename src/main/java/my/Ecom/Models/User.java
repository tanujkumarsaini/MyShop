package my.Ecom.Models;



import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
public class User implements UserDetails {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int userId;

@Column(nullable = false)
private String userName;

//consider email is user name
//important
//must be unique that we  can identify the user

@Column(unique = true,nullable = false)
private String userEmail;

@Column(nullable = false)
private String userPassword;

@Column(nullable = false)
private String gender;

@Column(nullable = false)
private long userMobile;

@Column(nullable = false)
private int userPincode;

private String userAddress;

private Date createAt;

private boolean active;

@OneToOne(mappedBy = "user")
private Cart cart;

@ManyToMany(fetch = FetchType.EAGER)
private Set<Role> roles=new HashSet<>();


public User() {
	super();
	// TODO Auto-generated constructor stub
}

public User(int userId, String userName, String userEmail, String userPassword, long userMobile, int userPincode,
		String userAddress, Date createAt, boolean active) {
	super();
	this.userId = userId;
	this.userName = userName;
	this.userEmail = userEmail;
	this.userPassword = userPassword;
	this.userMobile = userMobile;
	this.userPincode = userPincode;
	this.userAddress = userAddress;
	this.createAt = createAt;
	this.active = active;
}

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
	return userMobile;
}

public void setUserMobile(long userMobile) {
	this.userMobile = userMobile;
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

public Cart getCart() {
	return cart;
}

public void setCart(Cart cart) {
	this.cart = cart;
}







public Set<Role> getRoles() {
	return roles;
}

public void setRoles(Set<Role> roles) {
	this.roles = roles;
}

//important method for providing authority
@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
	List<SimpleGrantedAuthority> authorities = this.roles.stream().map(role-> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	return authorities;
}

@Override
public String getPassword() {
	// TODO Auto-generated method stub
	System.out.println(userPassword+"   getter");
	return userPassword;
}

@Override
public String getUsername() {
	// TODO Auto-generated method stub
	System.out.println(userEmail+"    setter");
	return this.userEmail;
}

@Override
public boolean isAccountNonExpired() {
	// TODO Auto-generated method stub
	return true;
}

@Override
public boolean isAccountNonLocked() {
	// TODO Auto-generated method stub
	return true;
}

@Override
public boolean isCredentialsNonExpired() {
	// TODO Auto-generated method stub
	return true;
}

@Override
public boolean isEnabled() {
	// TODO Auto-generated method stub
	return true;
}






}
