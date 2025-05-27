package com.example.demo.config;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptionUtils {
    private static final String ALGO = "AES";

    private static SecretKeySpec getKey(String myKey) {
        byte[] keyBytes = myKey.getBytes();
        return new SecretKeySpec(keyBytes, ALGO);
    }

    ;

    public static String encrypt(String data, String secret) {
        try {
            SecretKeySpec keySpec = getKey(secret);
            Cipher cipher = Cipher.getInstance(ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encrypted = cipher.doFinal(data.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Errore del metodo encrypt", e);
        }

    }

    ;

    public static String decrypt(String encryptedData, String secret) {
        try {
            SecretKeySpec keySpec = getKey(secret);
            Cipher cipher = Cipher.getInstance(ALGO);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decoded = Base64.getDecoder().decode(encryptedData);
            return new String(cipher.doFinal(decoded), "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException("Errore del metodo decrypt", e);
        }
    }

    ;
}
