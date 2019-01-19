package part3;

import java.io.IOException;

public class HelpJava {
    public static void main (String[] args){
        char inp;
        try {
            System.out.println("Справка.");
            System.out.println("1. if");
            System.out.println("2. switch");
            System.out.print("Выбирете: ");
            inp = (char) System.in.read();

            if (inp == '1') {
                System.out.println("if - условное ветвление.");
                System.out.println("if (условие) оператор");
                System.out.println("else оператор");
            } else if ( inp == '2') {
                System.out.println("switch - выбор ветвления");
                System.out.println("switch (выражение) {");
                System.out.println("\tcase константа: ");
                System.out.println("\tпоследовательность операторов");
                System.out.println("\tbreak;");
                System.out.println("\t// ...");
                System.out.println("}");
            } else {
                System.out.println("Раздел не найден.");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
