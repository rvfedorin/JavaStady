/*
 * Proprietary software.
 * 
 */
package rv.fedorin.spring.demoaop.dao;

import org.springframework.stereotype.Component;

/**
 *
 * @author R. V. Fedorin
 */
@Component
public class AccountDAO {
    
    public void addAccount() {
        System.out.println("[OUT] " + getClass() + " doing my DB work: adding an account");
    }
}
