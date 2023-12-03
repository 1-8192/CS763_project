package edu.bu.metcs673.trackr.common;

import edu.bu.metcs673.trackr.security.EncryptionUtility;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;

public class EncryptionStringConverter implements AttributeConverter<String,String> {
    @Autowired
    EncryptionUtility encryptionUtility;

    @Override
    public String convertToDatabaseColumn(String s) {
        return encryptionUtility.encryptString(s);
    }

    @Override
    public String convertToEntityAttribute(String s) {
        return encryptionUtility.decryptString(s);
    }
}
