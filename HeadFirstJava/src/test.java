import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class test implements Serializable {


    public static void main(String[] args) {
//        ArrayList<Object> obj = new List<Object>();

        try {
            FileWriter fw = new FileWriter("test.txt");
            fw.write("Моя первая строка!!!");
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    } // public static void main
}

