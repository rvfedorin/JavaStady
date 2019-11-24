/*
 * Created by Roman V. Fedorin
 */
package frv.org.patterns.Command;

/**
 *
 * @author Wolf
 */
public class GarageDoor {

    public void up() {
        System.out.println("[action] Doors is opened.");
    }

    public void down() {
        System.out.println("[action] Doors is closed.");
    }

    public void stop() {
        System.out.println("[action] Doors is stopped.");
    }

    public void lightOn() {
        System.out.println("[action] Garage's light is on.");
    }

    public void lightOff() {
        System.out.println("[action] Garage's light is off.");
    }

}
