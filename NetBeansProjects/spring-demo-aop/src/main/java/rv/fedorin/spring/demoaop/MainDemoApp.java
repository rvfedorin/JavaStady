/*
 * Proprietary software.
 * 
 */
package rv.fedorin.spring.demoaop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import rv.fedorin.spring.demoaop.dao.AccountDAO;

/**
 *
 * @author R. V. Fedorin
 */
public class MainDemoApp {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(DemoConfig.class);
        
        AccountDAO theAccoutnDAO = context.getBean("accountDAO", AccountDAO.class);
        theAccoutnDAO.addAccount();
        
        System.out.println("[OUT] one more time");
        theAccoutnDAO.addAccount();
        
        context.close();
    }
}
