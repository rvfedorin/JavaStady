/*
 * Proprietary software.
 * 
 */
package rv.fedorin.spring.demoaop.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 *
 * @author R. V. Fedorin
 */
@Aspect
@Component
public class MyDemoLoggingAspect {
    
    @Before("execution(public void addAccount())")
    public void beforeAddAccountAdvice() {
        System.out.println("\n[OUT] ========> Executing @Before advice on addAccount()");
    }
}
