/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frv.org.patterns.State;

/**
 *
 * @author wolf
 */
public class WinnerState implements State {

    private GumbleMachine gumbleMachine;

    public WinnerState(GumbleMachine gumbleMachine) {
        this.gumbleMachine = gumbleMachine;
    }

    @Override
    public void dispense() {
        System.out.println("You're winneer!!! You get two gumballs!");
        gumbleMachine.releaseBall();
        if (gumbleMachine.getCount() == 0) {
            gumbleMachine.setState(gumbleMachine.getSoldOutState());
        } else {
            gumbleMachine.releaseBall();
            if (gumbleMachine.getCount() > 0) {
                gumbleMachine.setState(gumbleMachine.getNoQuarterState());
            } else {
                System.out.println("Oops, out of gumbles.");
                gumbleMachine.setState(gumbleMachine.getSoldOutState());
            }
        }
    }

    @Override
    public String toString() {
        return "WinnerState";
    }
    
    

}
