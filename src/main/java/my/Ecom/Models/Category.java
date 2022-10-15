package my.Ecom.Models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.ManyToAny;

@Entity
public class Category {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int categoryId;
private String catgoryTitle;

@OneToMany(mappedBy = "category",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
private Set<Product> products=new HashSet<>();


public Category() {
	super();
	// TODO Auto-generated constructor stub
}


public Category(int categoryId, String catgoryTitle, String categoryDesc) {
	super();
	this.categoryId = categoryId;
	this.catgoryTitle = catgoryTitle;

}


public String getCatgoryTitle() {
	return catgoryTitle;
}
public void setCatgoryTitle(String catgoryTitle) {
	this.catgoryTitle = catgoryTitle;
}



public int getCategoryId() {
	return categoryId;
}


public void setCategoryId(int categoryId) {
	this.categoryId = categoryId;
}




}
