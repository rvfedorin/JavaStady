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
public class NoQuarterState implements State {
    private final GumbleMachine gumbleMachine;

    public NoQuarterState(GumbleMachine gumbleMachine) {
        this.gumbleMachine = gumbleMachine;
    }
    

    @Override
    public void insertQuarter() {
        System.out.println("You inserted a quarter.");
        gumbleMachine.setState(gumbleMachine.getHasQuarterState());
    }

    @Override
    public void rejectQuarter() {
        System.out.println("You haven't inserted a quarter.");
    }

    @Override
    public void turnCranc() {
        System.out.println("You turned, but there is no a quarter.");
    }

    @Override
    public void dispense() {
        System.out.println("You need to pay first.");
    }

    @Override
    public String toString() {
        return "NoQuarterState{" + '}';
    }
    
    
}
