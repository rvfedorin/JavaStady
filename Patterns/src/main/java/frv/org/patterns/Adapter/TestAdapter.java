/*
 * Created by Roman V. Fedorin
 */
package frv.org.patterns.Adapter;

/**
 *
 * @author Wolf
 */
public class TestAdapter {
    public static void main(String[] args) {
        WildTurkey wildTurkey = new WildTurkey();
        MallardDuck mallardDuck = new MallardDuck();
        MallardDuck mallardDuck2 = new MallardDuck();
        
        Duck[] ducks = new Duck[3];
        ducks[0] = mallardDuck;
        ducks[1] = mallardDuck2;
        ducks[2] = new TurkeyAdapter(wildTurkey);
        
        for(Duck one: ducks) {
            one.quack();
            one.fly();
        }
    }
}
