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
public interface State {

    default void insertQuarter() {
        System.out.println("State error... ");
    }

    default void rejectQuarter() {
        System.out.println("State error... ");
    }

    default void turnCranc() {
        System.out.println("State error... ");
    }

    default void dispense() {
        System.out.println("State error... ");
    }
}
