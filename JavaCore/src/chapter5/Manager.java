package chapter5;

public class Manager extends Employee {
    private double bonus;

    public Manager(String name, double salary, int day, int month, int year){
        super(name, salary, day, month, year);
        bonus = 0;
    }

    public double getSalary(){
        return super.getSalary() + bonus;
    }

    public void setBonus(double newBonus) {
        bonus = newBonus;
    }
}
