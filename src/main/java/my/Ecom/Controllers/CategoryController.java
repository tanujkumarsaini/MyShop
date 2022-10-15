package my.Ecom.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import my.Ecom.Models.Category;
import my.Ecom.Services.CategoryService;

@RestController
@CrossOrigin
public class CategoryController {
@Autowired
private CategoryService categoryService;

@PostMapping("/category")
public Category createCategory(@RequestBody Category category) {
	Category createCategory = categoryService.createCategory(category);
	return createCategory;
}

@GetMapping("/category/{categoryId}")
public Category getCategory(@PathVariable int categoryId) {
	Category category = categoryService.getCategory(categoryId);
	return category;
}

@GetMapping("/category")
public List<Category> getAllCategory(){
	List<Category> allCategory = categoryService.getAllCategory();
	return allCategory;
}

@DeleteMapping("/category/{categoryId}")
public String deleteCategory(@PathVariable int categoryId) {
	categoryService.deleteCategory(categoryId);
	return "Category deleted successfully";
}

@PutMapping("/category/{categoryId}")
public Category updateCategory(@RequestBody Category newCategory,@PathVariable int categoryId) {
	Category updatedCategory = categoryService.updateCategory(newCategory, categoryId);
	return updatedCategory;
}

}
