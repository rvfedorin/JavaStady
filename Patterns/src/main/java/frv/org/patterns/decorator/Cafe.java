/*
 * Created by Roman V. Fedorin
 */
package frv.org.patterns.decorator;

/**
 *
 * @author Wolf
 */
public class Cafe {
    public static void main(String[] args) {
        Beverage beverage = new Whip(new StrongCoffee());
        System.out.println(beverage.getName() + "; cost = " + beverage.cost());
        beverage = new Whip(beverage);
        System.out.println(beverage.getName() + "; cost = " + beverage.cost());
    }
}
