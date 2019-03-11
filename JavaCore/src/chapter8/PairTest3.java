package chapter8;

import chapter5.*;

public class PairTest3 {
    public static void main(String[] args) {
        Manager ceo = new Manager("Gus Greedy", 800000, 15, 12, 2003);
        Manager cfo = new Manager("Sid Sneaky", 600000, 15, 12, 2003);

        Pair<Manager> buddies = new Pair<>(ceo, cfo);
        printBuddies(buddies);

        ceo.setBonus(1000000);
        cfo.setBonus(500000);
        Manager[] managers = {ceo, cfo};

        Pair<Employee> result = new Pair<>();

        minmaxBonus(managers, result);
        printBuddies(result);

        maxminBonus(managers, result);
        printBuddies(result);


    } // close main

    public static void printBuddies(Pair<? extends Employee> p) {
        Employee first = p.getFirst();
        Employee second = p.getSecond();

        System.out.println(first.getName() + " and " + second.getName() + " is buddies.");
    } // close printBuddies

    public static void minmaxBonus(Manager[] m, Pair<? super Manager> result) {
        if (m.length <= 0) return;
        Manager min = m[0];
        Manager max = m[0];

        for (int i = 0; i < m.length; i++) {
            if (min.getBonus() > m[i].getBonus()) min = m[i];
            if (max.getBonus() < m[i].getBonus()) max = m[i];
        }

        result.setFirst(min);
        result.setSecond(max);
    } // close minmaxBonus

    public static void maxminBonus(Manager[] m, Pair<? super Manager> result) {
        minmaxBonus(m, result);
        PairAlg.swap(result);
    }
} // close PairTest3

class PairAlg {

    public static boolean hasNull(Pair<?> p) {
        return p.getFirst() == null || p.getSecond() == null;
    } // close hasNull

    public static void swap(Pair<?> p) {swapHelper(p);}

    public static <T> void swapHelper(Pair<T> p) {
        T t = p.getFirst();
        p.setFirst(p.getSecond());
        p.setSecond(t);
    }
} // close class PairAlg
