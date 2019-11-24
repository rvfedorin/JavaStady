/*
 * Created by Roman V. Fedorin
 */
package frv.org.patterns.factory;

import java.util.ArrayList;

/**
 *
 * @author Wolf
 */
public abstract class Pizza {
    String name;
    String dough;
    String sauce;
    ArrayList<String> toppings = new ArrayList<>();
    
    void prepare() {
        System.out.println(" [pizza] Preparing " + name);
        System.out.println(" [pizza] Tossing dough ... ");
        System.out.println(" [pizza] Adding souce ... ");
        System.out.println(" [pizza] Adding toppings ... ");
        for(int i = 0; i < toppings.size(); i++) {
            System.out.println(" [pizza]     " + toppings.get(i));
        }
    }
    
    void bake() {
        System.out.println(" [pizza] Bake for 25 minutes at 350");
    }
    
    void cut() {
        System.out.println(" [pizza] Cutting the pizza into diagonal slice");
    }
    
    void box() {
        System.out.println(" [pizza] Place pizza in official PizzaStore box");
    }
    
    String getName() {
        return name;
    }
}
