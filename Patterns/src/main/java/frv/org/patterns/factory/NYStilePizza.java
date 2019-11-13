/*
 * Created by Roman V. Fedorin
 */
package frv.org.patterns.factory;

/**
 *
 * @author Wolf
 */
public class NYStilePizza extends Pizza{

    public NYStilePizza() {
        name = "NY Stile Sauce and Cheese Pizza";
        dough = "Thin Crust Dough";
        sauce = "Marinara Sauce";
        
        toppings.add("Greated Reggiano Cheese");
    }
    
}
