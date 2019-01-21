package part3;

import java.io.IOException;

public class HelpJava {
    public static void main (String[] args){
        char inp, unnecessary;
        boolean exit;
        try {
            do {
                exit = false;
                for (int i = 0; i < 20; i++) {
                    System.out.print("=");
                }
                System.out.println();

                System.out.println("Справка.");
                System.out.println("Для выхода введите q");
                System.out.println("\t1. \tif");
                System.out.println("\t2. \tswitch");
                System.out.println("\t3. \tfor");
                System.out.println("\t4. \twhile");
                System.out.print("Выбирете: ");

                inp = (char) System.in.read();
                do {
                    unnecessary = (char) System.in.read();
                } while (unnecessary != '\n');

                if (inp == '1') {
                    System.out.println("if - условное ветвление.");
                    System.out.println("if (условие) оператор");
                    System.out.println("else оператор");
                } else if (inp == '2') {
                    System.out.println("switch - выбор ветвления");
                    System.out.println("switch (выражение) {");
                    System.out.println("\tcase константа: ");
                    System.out.println("\tпоследовательность операторов");
                    System.out.println("\tbreak;");
                    System.out.println("\t// ...");
                    System.out.println("}");
                } else if (inp == '3') {
                    System.out.println("for - цикл");
                    System.out.println("for (инициализация; условие; итерация) {");
                    System.out.println("\tпоследовательность операторов");
                    System.out.println("}");
                } else if (inp == '4') {
                    System.out.println("while - цикл");
                    System.out.println("while (условие) {");
                    System.out.println("\tпоследовательность операторов");
                    System.out.println("}");
                } else if (inp == 'q') {
                    System.out.println("Выход.");
                } else {
                    System.out.println("Раздел не найден.");
                    exit = true;
                }
            } while(exit);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
