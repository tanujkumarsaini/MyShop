package my.Ecom.Services.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.Ecom.Models.Category;
import my.Ecom.Repositories.CategoryRepository;
import my.Ecom.Services.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService {
@Autowired
private CategoryRepository categoryRepository;
	@Override
	public Category createCategory(Category category) {
		Category savedCategory = categoryRepository.save(category);
		return savedCategory;
	}

	@Override
	public Category getCategory(int categoryId) {
		Category category = categoryRepository.findById(categoryId).get();
		return category;
	}

	@Override
	public List<Category> getAllCategory() {
		List<Category> allCategory = categoryRepository.findAll();
		return allCategory;
	}

	@Override
	public void deleteCategory(int categoryId) {
		Category category = categoryRepository.findById(categoryId).get();
		categoryRepository.delete(category);
	}

	@Override
	public Category updateCategory(Category newCategory, int categoryId) {
		Category category = categoryRepository.findById(categoryId).get();
		category.setCatgoryTitle(newCategory.getCatgoryTitle());
		Category updatedCategory = categoryRepository.save(category);
		return updatedCategory;
	}

}
