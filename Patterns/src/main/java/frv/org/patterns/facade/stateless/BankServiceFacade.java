/*
 * Created by Roman V. Fedorin
 */
package frv.org.patterns.facade.stateless;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Wolf
 */
@Stateless
public class BankServiceFacade {
    
    @Inject
    CustomerService customerService;
    
    @Inject
    LoanService loanService;
    
    @Inject
    AccountService accountService;
    
    public boolean getLoan(int sessiongId, double amount) {
        boolean result = false;
        long id = customerService.getCustomer(sessiongId);
        
        if(customerService.checkId(id)) {
            if(loanService.checkCreditRaiting(id, amount)) {
                if(accountService.getLoan(amount)) {
                    result = accountService.setCustomerBalance(id, amount);
                }
            }
        }
        
        return result;
    }
}
