package chapter5;

public class ManagerTest {
    public static void main(String[] args) {
        Manager boss = new Manager("Boss", 80000, 22, 4, 2019);
        boss.setBonus(5000);

        Employee[] staff = new Employee[3];

        staff[0] = boss;
        staff[1] = new Employee("Tom", 40000, 12, 8, 2019);
        staff[2] = new Employee("Nancy", 40000, 12, 8, 2019);

        for(Employee e: staff) {
            System.out.println(e.getSalary());
        }
    }
}
