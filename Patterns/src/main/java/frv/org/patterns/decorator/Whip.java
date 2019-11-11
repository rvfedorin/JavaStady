/*
 * Created by Roman V. Fedorin
 */
package frv.org.patterns.decorator;

/**
 *
 * @author Wolf
 */
public class Whip implements Beverage {

    private final Beverage beverage;
    private String name;
    private double cost;

    public Whip(Beverage beverage) {
        this.beverage = beverage;
        this.name = "Whip";
        this.cost = 0.2;

        this.cost += beverage.cost();
        if (beverage.getName().contains(name)) {
            name = beverage.getName().replace(name, "Double " + name);
        } else {
            name = beverage.getName() + " with " + name;
        }
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
