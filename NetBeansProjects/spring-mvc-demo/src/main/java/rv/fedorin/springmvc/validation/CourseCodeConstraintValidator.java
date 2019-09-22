package rv.fedorin.springmvc.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
/**
 *
 * @author R. V. Fedorin
 */
public class CourseCodeConstraintValidator
        implements ConstraintValidator<CourseCode, String> {
    
    private String coursePrefix;

    @Override
    public void initialize(CourseCode courseCode) {
        this.coursePrefix = courseCode.value();
    }

    @Override
    public boolean isValid(String theCode, ConstraintValidatorContext theConstraintValidatorContext) {
        boolean result = theCode.startsWith(coursePrefix);
        
        return result;
    }

    
}
