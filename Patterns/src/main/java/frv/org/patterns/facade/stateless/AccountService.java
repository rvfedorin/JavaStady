/*
 * Created by Roman V. Fedorin
 */
package frv.org.patterns.facade.stateless;

import javax.ejb.Stateless;

/**
 *
 * @author Wolf
 */
@Stateless
public class AccountService {
    
    public boolean getLoan(double amount) {
        // check if bank has enough money
        return true;
    }
    
    public boolean setCustomerBalance(long id, double amount) {
        return true;
    }
}
