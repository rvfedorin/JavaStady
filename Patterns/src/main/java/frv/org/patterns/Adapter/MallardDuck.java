/*
 * Created by Roman V. Fedorin
 */
package frv.org.patterns.Adapter;

/**
 *
 * @author Wolf
 */
public class MallardDuck implements Duck {

    @Override
    public void quack() {
        System.out.println("Quack quack");
    }

    @Override
    public void fly() {
        System.out.println("Long fly");
    }
    
}
