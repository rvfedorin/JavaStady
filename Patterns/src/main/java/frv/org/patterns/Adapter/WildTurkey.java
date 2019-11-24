/*
 * Created by Roman V. Fedorin
 */
package frv.org.patterns.Adapter;

/**
 *
 * @author Wolf
 */
public class WildTurkey implements Turkey {

    @Override
    public void gobble() {
        System.out.println("Goble gobble");
    }

    @Override
    public void fly() {
        System.out.println("short fly");
    }
    
}
