package tools;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class CryptDecrypt {
    private static final String IV = "31415926";

    public static void main(String[] args) {
        // Configuration
        String key	= "pass";
        String secret = "string";
        String secretCod = "0csNHs7T74c=";

        String mode = "crypt"; // encrypt

        if (mode.equals("crypt")) {
            // Encrypt
            String result = getCrypt(key, secret);
            System.out.println(result);
        } else {
            // Decrypt
            String result = getEncrypt(key, secretCod);
            System.out.println(result);
        }
    } // ** main()

    public static String getCrypt(String pass, String toCryptString) {
        SecretKeySpec secKey = new SecretKeySpec(pass.getBytes(), "Blowfish");
        byte[] encoding;
        try {
            Cipher cipher = Cipher.getInstance("Blowfish/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secKey, new javax.crypto.spec.IvParameterSpec(IV.getBytes()));
            encoding = cipher.doFinal(toCryptString.getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error";
        }
        return new String(Base64.getEncoder().encode(encoding));
    }

    public static String getEncrypt(String pass, String toEncryptString) {
        SecretKeySpec secKey = new SecretKeySpec(pass.getBytes(), "Blowfish");
        byte[] message;
        try {
            Cipher cipher = Cipher.getInstance("Blowfish/CBC/PKCS5Padding");
            byte[] cryptoText = Base64.getDecoder().decode(toEncryptString);
            cipher.init(Cipher.DECRYPT_MODE, secKey, new javax.crypto.spec.IvParameterSpec(IV.getBytes()));
            message = cipher.doFinal(cryptoText);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error";
        }
        return new String(message);
    }

    public static boolean correct(String getPass, String realPass) {
        boolean result = false;
        String cryptGetPass = getCrypt(getPass, getPass);

        if(cryptGetPass.equals(realPass)) result = true;

        return result;
    }

} // ** class CryptDecrypt
