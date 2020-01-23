/*
 *  Created by Roman V. Fedorin
 */
package frv.org.patterns.MVC;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author wolf
 */
public class DJView implements ActionListener, BPMObserver, BeatObserver {
    BeatModelInterface model;
    ControllerInterface controller;
    
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateBPM() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateBeat() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
