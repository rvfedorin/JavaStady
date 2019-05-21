package other;

import MyWork.Tools.CryptDecrypt;

import static MyWork.Config.SW_PASS;

public class test {
    public static void main(String[] args) {
        System.out.println(CryptDecrypt.getEncrypt("", SW_PASS));
    }
}


// repeat text /////////////////////////////////////////////////////////////////////////////////
//            String space = " ";
//            k = String.join("", Collections.nCopies(lengthLabel - k.length(), space)) + k;
///////////////////////////////////////////////////////////////////////////////////////////////