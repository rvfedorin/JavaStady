
public class MakePizza {

    public static void main(String[] args) {
        NYPizza nyPizza = new NYPizza.Builder(22).addAddons(Topping.HAM).addAddons(Topping.ONION).build();
        System.out.println(nyPizza.addons);
    }
}
