/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frv.org.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wolf
 */
public class TestMyClass {

    public static void main(String[] args) {
        MyClass myClass = new MyClass();
        int num = myClass.getNumber();
        String nam = null;

        System.out.println(num + " " + nam);

        try {
            
            Field[] fields = myClass.getClass().getDeclaredFields();
            Method[] methods = myClass.getClass().getDeclaredMethods();

            System.out.println("Поля: ");
            for (Field f : fields) {
                int mod = f.getModifiers();
                String modif = "";
                if(Modifier.isPrivate(mod)) {
                    modif = "private ";
                } else if (Modifier.isPublic(mod)) {
                    modif = "public ";
                }
                System.out.println("\t" + modif + f.getType().getSimpleName() + " " + f.getName());
            }
            
            System.out.println("Методы: ");
            for (Method m : methods) {
                int mod = m.getModifiers();
                String modif = "";
                if(Modifier.isPrivate(mod)) {
                    modif = "private ";
                } else if (Modifier.isPublic(mod)) {
                    modif = "public ";
                }
                
                String returnType = m.getReturnType().getSimpleName() + " ";

                System.out.print("\t" + modif + returnType + m.getName() + "(");
                boolean notFirst = false;
                for (Parameter p : m.getParameters()) {
                    System.out.print(p.getType().getSimpleName() + " " + p.getName());
                    if (notFirst) {
                        System.out.print(", ");
                    }
                    notFirst = true;
                }
                System.out.print(")");
                System.out.println("");
            }
            System.out.println("");
            
            Method printM = myClass.getClass().getDeclaredMethod("printData");
            printM.setAccessible(true);
            printM.invoke(myClass);

            Field field = myClass.getClass().getDeclaredField("name");
            field.setAccessible(true);
            nam = (String) field.get(myClass);

            System.out.println(num + " " + nam);
            field.set(myClass, "New_Name");
            System.out.println(num + " " + field.get(myClass));

            // ============================================== //
            System.out.println("");
            System.out.println(String.join("+", Collections.nCopies(20, "=")));
            System.out.println("");

            Class clazz = Class.forName(MyClass.class.getName());
            myClass = (MyClass) clazz.newInstance();
            Method m = myClass.getClass().getDeclaredMethod("printData");
            m.setAccessible(true);
            m.invoke(myClass);

            // ============================================== //
            System.out.println("");
            System.out.println(String.join("+", Collections.nCopies(20, "=")));
            System.out.println("");

            Constructor[] constr = clazz.getConstructors();
            for (Constructor c : constr) {
                System.out.print(clazz.getSimpleName() + "(");
                boolean second = false;
                for (Parameter p : c.getParameters()) {
                    if (second) {
                        System.out.print(", ");
                    }
                    second = true;
                    System.out.print(p.getType().getSimpleName() + " " + p.getName());
                }
                System.out.print(")\n");
            }

        } catch (NoSuchFieldException
                | IllegalAccessException
                | InvocationTargetException
                | ClassNotFoundException
                | InstantiationException
                | NoSuchMethodException ex) {
            Logger.getLogger(MyClass.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
