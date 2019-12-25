/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frv.org.patterns.State;

import java.util.Random;

/**
 *
 * @author wolf
 */
public class HasQuarterState implements State {

    private GumbleMachine gumbleMachine;
    private final Random randomWinner = new Random(System.currentTimeMillis());

    public HasQuarterState(GumbleMachine gumbleMachine) {
        this.gumbleMachine = gumbleMachine;
    }

    @Override
    public void insertQuarter() {
        System.out.println("You can't insert another quarter.");
        gumbleMachine.setState(gumbleMachine.getHasQuarterState());
    }

    @Override
    public void rejectQuarter() {
        System.out.println("Quarter has been returned.");
        gumbleMachine.setState(gumbleMachine.getNoQuarterState());
    }

    @Override
    public void turnCranc() {
        System.out.println("You turned ...");
        int winner = randomWinner.nextInt(10);
        if (winner == 0 && gumbleMachine.getCount() > 1) {
            gumbleMachine.setState(gumbleMachine.getWinnerState());
        } else {
            gumbleMachine.setState(gumbleMachine.getSoldState());
        }
    }

    @Override
    public void dispense() {
        System.out.println("No gumble dispensed.");
    }

    @Override
    public String toString() {
        return "HasQuarterState{" + '}';
    }

    
}
