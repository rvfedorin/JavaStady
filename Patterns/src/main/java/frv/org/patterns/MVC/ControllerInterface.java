/*
 *  Created by Roman V. Fedorin
 */
package frv.org.patterns.MVC;

/**
 *
 * @author wolf
 */
interface ControllerInterface {
    void stop();
    void start();
    void increaseBPM();
    void decreaseBPM();
    void setBPM(int bpm);
}
