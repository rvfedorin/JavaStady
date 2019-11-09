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
public class LoanService {
    
    public boolean checkCreditRaiting(long id, double amount) {
        return true;
    }
    
}
