package my.Ecom.payload;

public class CategoryDto {
	private int categoryId;
	private String catgoryTitle;
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getCatgoryTitle() {
		return catgoryTitle;
	}
	public void setCatgoryTitle(String catgoryTitle) {
		this.catgoryTitle = catgoryTitle;
	}
	public CategoryDto(int categoryId, String catgoryTitle) {
		super();
		this.categoryId = categoryId;
		this.catgoryTitle = catgoryTitle;
	}
	public CategoryDto() {
		super();
		// TODO Auto-generated constructor stub
	}

}
