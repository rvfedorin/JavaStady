/*
 * Created by Roman V. Fedorin
 */
package frv.org.patterns.observer;

/**
 *
 * @author Wolf
 */
public interface Subject {
    public void registerObserver(MyObserver newObserver);
    public void removeObserver(MyObserver observer);
    public void notifyObservers();
}
