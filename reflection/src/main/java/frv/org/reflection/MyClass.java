/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frv.org.reflection;

/**
 *
 * @author Wolf
 */
public class MyClass {

    private String name = "default";
    private int number;

    public MyClass() {
    }
    
    public MyClass(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    private void printData() {
        System.out.println("MyClass{" + "name=" + name + ", number=" + number + '}');
    }
    
}
