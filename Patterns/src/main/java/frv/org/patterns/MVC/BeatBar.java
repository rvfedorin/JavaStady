/*
 *  Created by Roman V. Fedorin
 */
package frv.org.patterns.MVC;

import javax.swing.JProgressBar;

/**
 *
 * @author wolf
 */
class BeatBar extends JProgressBar implements Runnable {

    private static final long serialVersionUID = 2L;
    Thread thread;

    public BeatBar() {
        thread = new Thread(this);
        setMaximum(100);
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            int value = getValue();
            value = (int) (value * .75);
            setValue(value);
            repaint();
            try {
                Thread.sleep(50);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
