/*
 * Proprietary software.
 * 
 */
package rv.fedorin.web.customer.tracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author R. V. Fedorin
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {
    
    @RequestMapping("/list")
    public String listCustomers(Model theModel) {
        return "list-customers";
    }
}
