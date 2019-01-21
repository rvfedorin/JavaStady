package chapter5;

public class ManagerTest {
    public static void main(String[] args) {
        Manager boss = new Manager("Boss", 80000, 22, 4, 2019);
        boss.setBonus(5000);

        Employee[] staff = new Employee[3];

        System.out.println(boss.getClass());

        staff[0] = boss;
        staff[1] = new Employee("Tom", 40000, 12, 8, 2019);
        staff[2] = new Employee("Nancy", 40000, 12, 8, 2019);

        for(Employee e: staff) {
            System.out.println(e.getSalary());
            System.out.println("instanceof: " + (e instanceof Employee));
            System.out.println("Class: " + e.getClass());
            System.out.println(e);
            System.out.println("============================");
        }

        Manager[] man = new Manager[2];
        Employee[] emp = new Employee[2];

        emp[0] = new Employee("Tom", 40000, 12, 8, 2019);

        System.out.println(emp[0].getSalary());

    }
}
