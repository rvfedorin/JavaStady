package MyWork;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;

public class CryptDecrypt {
    public static void main(String[] args) throws Exception{
        // Configuration
        byte[] key	= "pass".getBytes();
        String IV  	= "12345678";

        String secret = "admin";
        String secretCod = "kktWH46TyRw=";

        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "Blowfish");
        Cipher cipher = Cipher.getInstance("Blowfish/CBC/PKCS5Padding");

        String mode = "crypt"; // encrypt


        if (mode.equals("1crypt")) {
            // Encrypt

            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new javax.crypto.spec.IvParameterSpec(IV.getBytes()));
            byte[] encoding = cipher.doFinal(secret.getBytes());

            System.out.println(DatatypeConverter.printBase64Binary(encoding));
        } else {
            // Decrypt
            byte[] cryptoText = DatatypeConverter.parseBase64Binary(secretCod);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new javax.crypto.spec.IvParameterSpec(IV.getBytes()));
            byte[] message = cipher.doFinal(cryptoText);

            System.out.println(new String(message));
        }


    } // ** main()
} // ** class CryptDecrypt
