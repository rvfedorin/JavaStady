/*
 * Created by Roman V. Fedorin
 */
package frv.org.patterns.factory;

/**
 *
 * @author Wolf
 */
public abstract class PizzaStore {
    
    public Pizza orderPizz(String type) {
        Pizza pizza;
        
        pizza = createPizza(type);
        
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        
        return pizza;
    }

    public abstract Pizza createPizza(String type);
}
