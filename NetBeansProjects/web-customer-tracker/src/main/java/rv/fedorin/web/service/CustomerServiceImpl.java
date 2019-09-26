/*
 * Proprietary software.
 * 
 */
package rv.fedorin.web.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rv.fedorin.web.dao.CustomerDAO;
import rv.fedorin.web.entity.Customer;

/**
 *
 * @author R. V. Fedorin
 */
@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    CustomerDAO customerDAO;
    
    @Override
    @Transactional
    public List<Customer> getCustomers() {
        return customerDAO.getCustomers();
    }

    @Override
    @Transactional
    public void saveCustomer(Customer customer) {
        customerDAO.saveCustomer(customer);
    }
    
    
}
