/*
 * Created by Roman V. Fedorin
 */
package frv.org.patterns.singleton;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

/**
 *
 * @author Wolf
 */
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Singleton
public class VisitCounter {

    private int count;

    @PostConstruct
    public void init() {
        System.out.println("Counter is made.");
        count = 0;
    }

    @Lock(LockType.WRITE)
    public void addVist() {
        System.out.println("Added one.");
        count++;
    }

    @Lock(LockType.READ)
    public int getCount() {
        return count;
    }
}
