/*
 * Created by Roman V. Fedorin
 */
package frv.org.patterns.factory;

/**
 *
 * @author Wolf
 */
public class NYStilePizzaStore extends PizzaStore {

    @Override
    public Pizza createPizza(String type) {
        Pizza pizza = null;
        if(type.equals("NYStile")) {
            pizza = new NYStilePizza();
        }
        
        return pizza;
    }
}
