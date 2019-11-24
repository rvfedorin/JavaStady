/*
 * Created by Roman V. Fedorin
 */
package frv.org.patterns.factory;

/**
 *
 * @author Wolf
 */
public class ChikagoStilePizza extends Pizza {

    public ChikagoStilePizza() {
        name = "Chicago Stile Deep Dish Cheese Pizza";
        dough = "Extra thick Crust Dough";
        sauce = "Plum Tomato Sauce";

        toppings.add("Shredded Mozzarella Cheese");
    }
    
    @Override
    void cut() {
        System.out.println(" [pizza] Cutting the pizza into square slices");
    }

}
