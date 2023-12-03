package edu.bu.metcs673.trackr.security;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
public class EncryptionUtility {

    private String key = "1234567812345678";
    private String vector = "1234567812345678";
    private String cryptoAlgo = "AES/CBC/PKCS5PADDING";

    private String algo = "AES";

    public String encryptString(String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(vector.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec spec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), algo);

            Cipher cipher = Cipher.getInstance(cryptoAlgo);
            cipher.init(Cipher.ENCRYPT_MODE, spec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.encodeBase64String(encrypted);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException
                 | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
    return null;
    }

    public String decryptString(String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(vector.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec spec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), algo);

            Cipher cipher = Cipher.getInstance(cryptoAlgo);
            cipher.init(Cipher.DECRYPT_MODE, spec, iv);

            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
            return new String(original);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException
                 | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String encryptDouble(double value) {
        return encryptString(Double.toString(value));
    }

    public double decryptDouble(String encrypted) {
        String decryptedValue = decryptString(encrypted);
        if (decryptedValue != null) {
            return Double.parseDouble(decryptedValue);
        }
        return Double.NaN; // Return NaN for failure or invalid input
    }
}
