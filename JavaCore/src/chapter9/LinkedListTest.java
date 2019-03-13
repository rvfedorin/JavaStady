package chapter9;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class LinkedListTest {
    public static void main(String[] args) {
        List<String> a = new LinkedList<>();
        a.add("Ann");
        a.add("Carl");
        a.add("Erica");

        List<String> b = new LinkedList<>();
        b.add("Bov");
        b.add("Carl");
        b.add("Tom");
        b.add("Sam");

        ListIterator<String> aIter = a.listIterator();
        Iterator<String> bIter = b.iterator();

        // marge the words from b into a
        while (bIter.hasNext()) {
            if(aIter.hasNext()) aIter.next();
            aIter.add(bIter.next());
        }

        System.out.println(a);

        // remove every second from b
        bIter = b.iterator();

        while (bIter.hasNext()) {
            bIter.next();
            if(bIter.hasNext()) {
                bIter.next();
                bIter.remove();
            }
        } // while (bIter.hasNext())

        System.out.println(b);

        // bulk operation: remove all elements in b from a

        a.removeAll(b);
        System.out.println(a);

    } // close main
} // close class LinkedListTest
