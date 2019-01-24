package chapter5;

import java.util.*;
import java.lang.reflect.*;

public class ReflectionTest {
    public static void main(String... args) {
        String name;

        if (args.length > 0) {
            name = args[0];
        } else {
            Scanner in = new Scanner(System.in);
            System.out.print("Input class name: ");
            name = in.next();
        }

        try {
            Class aClass = Class.forName(name);
            Class superClass = aClass.getSuperclass();
            String modifiers = Modifier.toString(aClass.getModifiers());

            if (modifiers.length() > 0) {
                System.out.print(modifiers + " ");
            }
            System.out.print("class " + name);
            if (superClass != null && superClass != Object.class) {
                System.out.print(" extends " + superClass.getName());
            }
            System.out.print("\n{\n");
            printConstructors(aClass);
            System.out.println();
            printMethods(aClass);
            System.out.println();
            printFields(aClass);
            System.out.println();

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        System.exit(0);
    } // close main()

    public static void printConstructors(Class aClass) {
        Constructor[] constructors = aClass.getDeclaredConstructors();

        for(Constructor c: constructors) {
            String name = c.getName();
            System.out.print("  ");
            String mod = Modifier.toString(c.getModifiers());
            if(mod.length() > 0) {
                System.out.print(mod + " ");
            }
            System.out.print(name + "(");

            Class[] paramTypes = c.getParameterTypes();
            for(int i = 0; i < paramTypes.length; i++) {
                if(i > 0) {
                    System.out.print(", ");
                }
                System.out.print(paramTypes[i].getName());
            } // for

            System.out.println(");");
        }
    } // close printConstructors()

    public static void printMethods(Class aClass) {
        Method[] methods = aClass.getDeclaredMethods();

        for (Method met : methods) {
            Class retType = met.getReturnType();
            String name = met.getName();

            System.out.print("  ");
            String modifier = Modifier.toString(met.getModifiers());
            if (modifier.length() > 0) {
                System.out.print(modifier + " ");
            }
            System.out.print(retType.getName() + " " + name + "(");
            Class[] paramType = met.getParameterTypes();
            for (int i = 0; i < paramType.length; i++) {
                if (i > 0) {
                    System.out.print(", ");
                }
                System.out.print(paramType[i].getName());
            } // close for
            System.out.println(");");
        }
    } // close printMethods()

    public static void printFields(Class cl) {
        Field[] fields = cl.getDeclaredFields();

        for(Field f: fields) {

            Class type = f.getType();
            String name = f.getName();
            System.out.print("  ");
            String modifiers = Modifier.toString(f.getModifiers());
            if (modifiers.length() > 0) {
                System.out.print(modifiers + " ");
            }
            System.out.println(type.getName() + " " + name);

        }
    } // close printFields()
}
