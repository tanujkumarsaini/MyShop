package my.Ecom.payload;

import java.util.ArrayList;
import java.util.List;

public class OrderResponse {
	
private List<OrderDto> content=new ArrayList<OrderDto>();

private int pageNumber;

private int pageSize;

private long totalElements;

private int totalPages;

private boolean lastPage;



public List<OrderDto> getContent() {
	return content;
}

public void setContent(List<OrderDto> content) {
	this.content = content;
}

public int getPageNumber() {
	return pageNumber;
}

public void setPageNumber(int pageNumber) {
	this.pageNumber = pageNumber;
}

public int getPageSize() {
	return pageSize;
}

public void setPageSize(int pageSize) {
	this.pageSize = pageSize;
}

public long getTotalElements() {
	return totalElements;
}

public void setTotalElements(long totalElements) {
	this.totalElements = totalElements;
}

public int getTotalPages() {
	return totalPages;
}

public void setTotalPages(int totalPages) {
	this.totalPages = totalPages;
}

public boolean isLastPage() {
	return lastPage;
}

public void setLastPage(boolean lastPage) {
	this.lastPage = lastPage;
}


}
