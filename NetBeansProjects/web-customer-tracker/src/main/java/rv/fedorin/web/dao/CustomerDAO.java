/*
 * Proprietary software.
 * 
 */
package rv.fedorin.web.dao;

import java.util.List;
import rv.fedorin.web.entity.Customer;

/**
 *
 * @author R. V. Fedorin
 */
public interface CustomerDAO {
    
    public List<Customer> getCustomers();

    public void saveCustomer(Customer customer);

    public Customer getCustomers(int theId);

    public void deleteCustomer(int theId);
}
