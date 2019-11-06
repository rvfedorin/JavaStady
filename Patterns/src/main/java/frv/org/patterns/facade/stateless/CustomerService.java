/*
 * Created by Roman V. Fedorin
 */
package frv.org.patterns.facade.stateless;

/**
 *
 * @author Wolf
 */
import javax.ejb.Stateless;

@Stateless
public class CustomerService {
    
    public long getCustomer(int sessionID) {
        // register customer's id
        return 100005L;
    }
    
    public boolean checkId(long x) {
        return true;
    }
}
