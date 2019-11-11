/*
 * Created by Roman V. Fedorin
 */
package frv.org.patterns.decorator;

/**
 *
 * @author Wolf
 */
public class Latte implements Beverage {
    private final String name;
    private final double cost;

    public Latte() {
        this.name = "Latte";
        this.cost = 1.2;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double cost() {
        return cost;
    }
    
}
