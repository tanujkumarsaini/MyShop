package my.Ecom.Exception;

public class ResourceNotFoundException extends RuntimeException{
public ResourceNotFoundException() {
	super("Resource you are looking not found on sserver!!");
}


public ResourceNotFoundException(String message) {
	super(message);
}
}
