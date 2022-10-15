package my.Ecom.Services;

import java.util.List;

import my.Ecom.Models.Category;

public interface CategoryService {
	public Category createCategory(Category category);
	public Category getCategory(int categoryId);
	public List<Category> getAllCategory();
	public void deleteCategory(int categoryId);
	public Category updateCategory(Category category,int categoryId);
}
