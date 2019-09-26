/*
 * Proprietary software.
 * 
 */
package rv.fedorin.web.service;

import java.util.List;
import rv.fedorin.web.entity.Customer;

/**
 *
 * @author R. V. Fedorin
 */
public interface CustomerService {
    
    public List<Customer> getCustomers();

    public void saveCustomer(Customer customer);
}
