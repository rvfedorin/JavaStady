package other;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class test {
    public static void main(String[] args) {

        try(BufferedReader fin=new BufferedReader(new InputStreamReader(new FileInputStream("test"))))
        {
            String i="";
            while(i != null){
                i = fin.readLine();
                System.out.print(i);
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }

    } // ** main()
}

// repeat text /////////////////////////////////////////////////////////////////////////////////
//            String space = " ";
//            k = String.join("", Collections.nCopies(lengthLabel - k.length(), space)) + k;
///////////////////////////////////////////////////////////////////////////////////////////////