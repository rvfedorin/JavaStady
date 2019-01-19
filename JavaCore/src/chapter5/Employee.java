package chapter5;

import java.time.LocalDate;

public class Employee {
    private String name;
    private double salary;
    private LocalDate hireDay;

    public Employee(String aName, double aSalary, int day, int month, int year) {
        name = aName;
        salary = aSalary;
        hireDay = LocalDate.of(year, month, day);
    }

    public String getName(){
        return name;
    }

    public double getSalary(){
        return salary;
    }

    public LocalDate getHireDay(){
        return hireDay;
    }

    private void setSalary(double newSalary) {
        salary = newSalary;
    }

    public void raiseSalary(double byPercent) {
        double raise = getSalary() * byPercent / 100;
        double newSalary = getSalary() + raise;
        setSalary(newSalary);
    }
}
