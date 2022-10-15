package my.Ecom.payload;

import java.util.List;

public class ProductSearchResponse {
	private List<ProductDto> content;

	public List<ProductDto> getContent() {
		return content;
	}

	public void setContent(List<ProductDto> content) {
		this.content = content;
	}
	
}
