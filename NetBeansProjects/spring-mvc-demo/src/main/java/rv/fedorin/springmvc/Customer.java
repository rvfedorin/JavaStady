/*
 * Proprietary software.
 * 
 */
package rv.fedorin.springmvc; 

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import rv.fedorin.springmvc.validation.CourseCode;

/**
 *
 * @author R. V. Fedorin
 */
public class Customer {
    @NotNull(message="is requared")
    @Size(min=1, max=100)
    private String firstName;
    
    @NotNull(message="is requared")
    @Size(min=1, max=100)
    private String lastName;
    
    @NotNull
    @Min(value=5)
    @Max(value=10)
    private Integer freePass;
    
    @Pattern(regexp="[a-zA-Z0-9]{5}", message="only 5 char/digits")
    private String postCode;
    
    @CourseCode(value="FRV", message="must start with FRV")
    private String courseCode;
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getFreePass() {
        return freePass;
    }

    public void setFreePass(Integer freePass) {
        this.freePass = freePass;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    
    
}
