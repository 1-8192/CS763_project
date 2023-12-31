package edu.bu.metcs673.trackr.security;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Encryption utility class to encrypt data for database storage.
 */
@Component
public class EncryptionUtility {

    // Bringing in the encryption key and initialization vector from application context.
    @Value("${encrypt_key}")
    private String key;

    @Value("${init_vector}")
    private String vector;

    // Using the AES symmetric algorithm for encryption. Used the standard Cipher transformation example from documentation
    // for AES 128 bit encryption.
    private final String cryptoAlgo = "AES/GCM/NoPadding";
    private final String algo = "AES";

    /**
     * Encrypt string types.
     * @param value String value we want to encrypt
     * @return encrypted string.
     * @throws RuntimeException
     */
    public String encryptString(String value) throws RuntimeException {
        try {
            GCMParameterSpec gcmSpec = new GCMParameterSpec(128, (vector.getBytes(StandardCharsets.UTF_8)));
            SecretKeySpec spec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), algo);

            Cipher cipher = Cipher.getInstance(cryptoAlgo);
            cipher.init(Cipher.ENCRYPT_MODE, spec, gcmSpec);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.encodeBase64String(encrypted);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException
                 | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method to decrypt string.
     * @param encrypted encrypted string
     * @return decrypted String
     * @throws RuntimeException
     */
    public String decryptString(String encrypted) throws RuntimeException {
        try {
            GCMParameterSpec gcmSpec = new GCMParameterSpec(128, (vector.getBytes(StandardCharsets.UTF_8)));
            SecretKeySpec spec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), algo);

            Cipher cipher = Cipher.getInstance(cryptoAlgo);
            cipher.init(Cipher.DECRYPT_MODE, spec, gcmSpec);

            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
            return new String(original);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException
                 | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Encrypt double types.
     * @param value double to encrypt.
     * @return encrypted string.
     */
    public String encryptDouble(double value) {
        return encryptString(Double.toString(value));
    }

    /**
     * Decrypt double.
     * @param encrypted encrypted string.
     * @return double that was decrypted.
     */
    public double decryptDouble(String encrypted) {
        String decryptedValue = decryptString(encrypted);
        if (decryptedValue != null) {
            return Double.parseDouble(decryptedValue);
        }
        return Double.NaN; // Return NaN for failure or invalid input
    }
}
