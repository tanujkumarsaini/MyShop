package CustomAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidatePhone implements ConstraintValidator<Phone, Long>{

	@Override
	public boolean isValid(Long mob, ConstraintValidatorContext context) {
		boolean flag=false;
	int size=mob.toString().length();
	System.out.println("size   "+size);
	
	if(size==10) {
		String temp = mob.toString();
		System.out.println(temp);
		String m =temp.substring(0,1);
		System.out.println(m);
		int d=Integer.parseInt(m);
		System.out.println("Integer value"+d);
		if(d>=6) {
			flag= true;
		}
		else {
			flag=false;
		}
		}
  
	return flag;
	}

}
