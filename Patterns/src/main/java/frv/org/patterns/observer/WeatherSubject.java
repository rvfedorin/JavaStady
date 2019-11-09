/*
 * Created by Roman V. Fedorin
 */
package frv.org.patterns.observer;

import java.util.ArrayList;

/**
 *
 * @author Wolf
 */
public class WeatherSubject implements Subject {

    private final ArrayList<MyObserver> observers;

    public WeatherSubject() {
        observers = new ArrayList();
    }

    @Override
    public void registerObserver(MyObserver newObserver) {
        observers.add(newObserver);
    }

    @Override
    public void removeObserver(MyObserver observer) {
        if (observers.contains(observer)) {
            observers.remove(observer);
        }
    }

    @Override
    public void notifyObservers() {
        observers.forEach((o) -> {
            o.update();
        });
    }

}
