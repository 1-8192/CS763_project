package edu.bu.metcs673.trackr.common;

import edu.bu.metcs673.trackr.security.EncryptionUtility;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;

/**
 * Class to encrypt doubles for db encryption.
 */
public class EncryptionDoubleConverter implements AttributeConverter<Double, String> {
    @Autowired
    EncryptionUtility encryptionUtility;

    @Override
    public String convertToDatabaseColumn(Double d) {
        return encryptionUtility.encryptDouble(d);
    }

    @Override
    public Double convertToEntityAttribute(String s) {
        return encryptionUtility.decryptDouble(s);
    }
}
