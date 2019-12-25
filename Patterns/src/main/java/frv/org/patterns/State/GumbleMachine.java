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
public class GumbleMachine {

    State soldOutState;
    State noQuarterState;
    State hasQuarterState;
    State soldState;
    State winnerState;

    State state = soldOutState;
    int count = 0;

    public GumbleMachine(int numberBulls) {
        this.soldOutState = new SoldOutState(this);
        this.noQuarterState = new NoQuarterState(this);
        this.hasQuarterState = new HasQuarterState(this);
        this.soldState = new SoldState(this);
        this.winnerState = new WinnerState(this);
        this.count = numberBulls;
        if (count > 0) {
            state = noQuarterState;
        }
    }

    void setState(State newState) {
        state = newState;
    }

    public State getSoldOutState() {
        return soldOutState;
    }

    public State getNoQuarterState() {
        return noQuarterState;
    }

    public State getHasQuarterState() {
        return hasQuarterState;
    }

    public State getSoldState() {
        return soldState;
    }

    public State getWinnerState() {
        return winnerState;
    }

    public int getCount() {
        return count;
    }
    

    public void insertQuarter() {
        state.insertQuarter();
    }

    public void rejectQuarter() {
        state.rejectQuarter();
    }

    public void turnCranc() {
        state.turnCranc();
    }

    public void releaseBall() {
        System.out.println("A gumball comes rolling out the slot ... ");
        if(count != 0) {
            count--;
        }
    }

    @Override
    public String toString() {
        return "GumbleMachine{" + "state=" + state + ", count=" + count + '}';
    }
    
    
}
