/*
 *  Created by Roman V. Fedorin
 */
package frv.org.patterns.MVC;

/**
 *
 * @author wolf
 */
public class DJTestDrive {
    public static void main(String[] args) {
        BeatModelInterface model = new BeatModel();
        ControllerInterface controller = new BeatController(model);

    }
}
