package rv.fedorin.springmvc;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author wolf
 */
@Controller
@RequestMapping("/hello")
public class HelloWorldController {
    
    // show a controller method to show initialization HTML form
    @RequestMapping("/showForm")
    public String showForm(){
        return "helloworld-form";
    }
    
    // show a controller method to process the form
    public String processForm(){
        return "helloworld";
    }
    
    // new a controller method to read form and 
    // add data to a model
    
    @RequestMapping("/processForm")
    public String letsShoutDude(HttpServletRequest request, Model model) {
        
        // read request parameter from the HTML form
        String theName = request.getParameter("studentName");
        
        // conver the data to all caps
        theName = theName.toUpperCase();
        
        // create a message 
        String result = "Yo! " + theName;

        // add the message to the model
        model.addAttribute("message", result);
        
        return "helloworld";
    }
    
    @RequestMapping("/processFormThree")
    public String processFormThree(
            @RequestParam("studentName")String theName, 
            Model model) {
        
        // conver the data to all caps
        theName = theName.toUpperCase();
        
        // create a message 
        String result = "Hey my friend! " + theName;

        // add the message to the model
        model.addAttribute("message", result);
        
        return "helloworld";
    }
}
