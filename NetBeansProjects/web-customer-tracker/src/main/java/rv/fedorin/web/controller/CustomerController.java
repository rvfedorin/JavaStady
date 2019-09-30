/*
 * Proprietary software.
 * 
 */
package rv.fedorin.web.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rv.fedorin.web.entity.Customer;
import rv.fedorin.web.service.CustomerService;

/**
 *
 * @author R. V. Fedorin
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;
    
    @GetMapping("/list")
    public String listCustomers(Model theModel) {
        
        List<Customer> customers = customerService.getCustomers();
        theModel.addAttribute("customers", customers);
        
        return "list-customers";
    }
    
    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "customer-form";
    }
    
    @PostMapping("/saveCustomer")
    public String saveCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.saveCustomer(customer);
        return "redirect:/customer/list";
    }
}
