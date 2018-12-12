import java.io.*;

class test implements Serializable {


    public static void main(String[] args) {
        try {
            FileWriter fw = new FileWriter("test.txt");
            fw.write("Моя первая строка!!!");
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    } // public static void main
}

