/*
 * Created by Roman V. Fedorin
 */
package frv.org.patterns.factory;

/**
 *
 * @author Wolf
 */
public class PizzaTestDrive {
    public static void main(String[] args) {
        PizzaStore nYStilePizza = new NYStilePizzaStore();
        PizzaStore chPizzaStore = new ChicagoStylePizzaStore();
        
        Pizza pizza = nYStilePizza.orderPizz("NYStile");
        System.out.println("Ordered a " + pizza.getName() + "\n");
        
        pizza = chPizzaStore.orderPizz("ChicagoStile");
        System.out.println("Ordered a " + pizza.getName() + "\n");
        
    }
}
