package chapter6;

import java.awt.*;
import java.util.*;

import javax.swing.Timer;
import javax.swing.*;

public class LambdaTest {
    public static void main(String[] args) {
        String[] planets = {"Venus", "Mercury", "Earth", "Mars", "Saturn", "Neptune"};
        System.out.println("Origin: ");
        System.out.println(Arrays.toString(planets));
        Arrays.sort(planets);
        System.out.println();
        System.out.println("After sort by word: ");
        System.out.println(Arrays.toString(planets));
        System.out.println();
        Arrays.sort(planets, (first, second)-> first.length() - second.length());
        System.out.println("Afrer sort by length:");
        System.out.println(Arrays.toString(planets));

        Timer t = new Timer(10000, event -> {
            System.out.println("timer: " + new Date());
            Toolkit.getDefaultToolkit().beep();
        });
        t.start();

        new JOptionPane().showMessageDialog(null,"Hit the button to quit.");
        System.exit(0);

        /* *** test of recursion ***************
        LambdaTest lt = new LambdaTest();
        String st = "12345";
        System.out.println(lt.revString(st));
        **************************************/

    }  // close main()

    /* *********************************************
    public String revString(String s) {
        if (s.length() == 1) {
            return s;
        }

        StringBuilder sb = new StringBuilder();
        for(int i=0; i<s.length()-1; i++) {
            sb.append(s.charAt(i));
        }

        StringBuilder sbNew = new StringBuilder();
        sbNew.append(s.charAt(s.length()-1));
        sbNew.append(revString(sb.toString()));

        return sbNew.toString();
    } // close revString(String s)
    ************************************************/
} // close class LambdaTest
