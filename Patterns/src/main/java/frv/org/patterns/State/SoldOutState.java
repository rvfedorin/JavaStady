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
public class SoldOutState implements State {
    private final GumbleMachine gumbleMachine;

    public SoldOutState(GumbleMachine gumbleMachine) {
        this.gumbleMachine = gumbleMachine;
    }
    
    @Override
    public void insertQuarter() {
        System.out.println("Closed.");
    }

    @Override
    public void rejectQuarter() {
        System.out.println("Closed.");
    }

    @Override
    public void turnCranc() {
        System.out.println("Closed.");
    }

    @Override
    public void dispense() {
        System.out.println("Closed.");
    }

    @Override
    public String toString() {
        return "SoldOutState{" + '}';
    }
    
    
}
