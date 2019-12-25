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
public class BumballMachTestDrive {
    public static void main(String[] args) {
        GumbleMachine gm = new GumbleMachine(5);
        System.out.println(gm);
        
        gm.insertQuarter();
        gm.turnCranc();
        
        System.out.println(gm);
        
        gm.insertQuarter();
        gm.turnCranc();
        gm.insertQuarter();
        gm.turnCranc();
        
        System.out.println(gm);
    }
}
