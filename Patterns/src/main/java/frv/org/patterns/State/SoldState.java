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
public class SoldState implements State {
    private final GumbleMachine gumbleMachine;

    public SoldState(GumbleMachine gumbleMachine) {
        this.gumbleMachine = gumbleMachine;
    }
    
    @Override
    public void insertQuarter() {
        System.out.println("Please wait, wa are already giving you a gumble.");
        gumbleMachine.setState(gumbleMachine.getHasQuarterState());
    }

    @Override
    public void rejectQuarter() {
        System.out.println("Sorry, you have already turned the crank.");
    }

    @Override
    public void turnCranc() {
        System.out.println("Turning twice doesn't get you another gumball.");
    }

    @Override
    public void dispense() {
        gumbleMachine.releaseBall();
        if(gumbleMachine.getCount() > 0) {
            gumbleMachine.setState(gumbleMachine.getNoQuarterState());
        } else {
            System.out.println("Oops, out of gumbles.");
            gumbleMachine.setState(gumbleMachine.getSoldOutState());
        }
    }

    @Override
    public String toString() {
        return "SoldState{" + '}';
    }
    
    
}
