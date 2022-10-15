package my.Ecom.Services;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

	public String  uploadFile(String path,MultipartFile file) throws Exception;
	public InputStream getResource(String path) throws IOException;
	public void deleteFile(String path);
	
}
