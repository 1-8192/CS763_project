package edu.bu.metcs673.trackr.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Encryption Utility Class.
 */
@SpringBootTest
class EncryptionUtilityTest {

    @Autowired
    EncryptionUtility encryptionUtility;

    @Test
    void testEncryptAndDecryptString() {
        String originalString = "This is a test";

        // Encrypt the string
        String encryptedString = encryptionUtility.encryptString(originalString);

        // Decrypt the string
        String decryptedString = encryptionUtility.decryptString(encryptedString);

        // Check if the decrypted string matches the original string
        assertEquals(originalString, decryptedString);
    }

    @Test
    void testEncryptAndDecryptDouble() {
        double originalDouble = 500.38;

        // Encrypt the double
        String encryptedDouble = encryptionUtility.encryptDouble(originalDouble);

        // Decrypt the double
        double decryptedDouble = encryptionUtility.decryptDouble(encryptedDouble);

        // Check if the decrypted double matches the original double
        assertEquals(originalDouble, decryptedDouble, 0.001); // You may adjust the delta based on precision requirements
    }
}

