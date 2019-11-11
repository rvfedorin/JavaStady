/*
 * Created by Roman V. Fedorin
 */
package frv.org.patterns.decorator;

/**
 *
 * @author Wolf
 */
public class StrongCoffee implements Beverage {
    private final String name;
    private final double cost;

    public StrongCoffee() {
        this.name = "Strong Coffee";
        this.cost = 1;
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
