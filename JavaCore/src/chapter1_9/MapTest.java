package chapter1_9;

import chapter1_5.Employee;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MapTest {
    public static void main(String[] args) {
        Map<String, Employee> staff = new HashMap<>();
        staff.put("1", new Employee("Gus Greedy", 800000, 15, 12, 2003));
        staff.put("2", new Employee("Sid Sneaky", 300000, 12, 11, 2013));
        staff.put("3", new Employee("Test Test", 100000, 11, 10, 2003));
        staff.put("4", new Employee("Test2 Test2", 1000, 11, 3, 2001));

        System.out.println(staff);

        staff.remove("2");
        staff.put("234", new Employee("Test4 Test4", 100440, 11, 3, 2001));

        System.out.println(staff.get("3"));
        System.out.println(Collections.nCopies(20, "="));

        staff.forEach((k, v) -> {
            System.out.println("[Key = " + k + "] [Value name = \"" + v.getName() + "\"]");
        });


    } // close main
} // close class MapTest
