/*
 * Created by Roman V. Fedorin
 */
package frv.org.patterns.observer;

import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Wolf
 */
public class CurrentConditionDisplay implements Observer {

    private final Observable observable;
    private float temperature;
    private float humidity;

    public CurrentConditionDisplay(Observable observable) {
        this.observable = observable;
        this.observable.addObserver(this);
    }

    @Override
    public void update(Observable obs, Object arg) {
        if (obs instanceof WeatherData) {
            WeatherData weatherData = (WeatherData) obs;
            this.temperature = weatherData.getTemperature();
            this.humidity = weatherData.getHumidity();
            display();
        }
    }

    private void display() {
        System.out.println("Temperature: " + temperature
                + "\n"
                + "Humidity: " + humidity + "\n");
    }

}
