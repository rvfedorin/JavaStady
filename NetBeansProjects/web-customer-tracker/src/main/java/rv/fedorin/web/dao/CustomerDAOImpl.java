/*
 * Proprietary software.
 * 
 */
package rv.fedorin.web.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import rv.fedorin.web.entity.Customer;

/**
 *
 * @author R. V. Fedorin
 */
@Repository
public class CustomerDAOImpl implements CustomerDAO{
    
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Customer> getCustomers() {
        Session session = sessionFactory.getCurrentSession();
        
        Query<Customer> theQuery;
        theQuery = session.createQuery("from Customer order by lastName", 
                Customer.class);
        
        List<Customer> custmers = theQuery.getResultList();
        
        return custmers;
    }

    @Override
    public void saveCustomer(Customer customer) {
        Session session = sessionFactory.getCurrentSession();
        session.save(customer);
    }
    
    
}
