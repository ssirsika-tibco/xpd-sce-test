/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.resources.util;

/**
 * This is basic String encryption utility class. It is used mainly password
 * obfuscation for password stored in files. Maximal string size is 256
 * characters.
 * <p>
 * <i>Created: 29 Mar 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class EncryptionUtil {

    private static final int DEFAULT_PASSWORD_ENCRIPTION_SIZE = 64;

    /**
     * Encrypts string.
     * 
     * @param plainString
     *            string to be encrypted.
     * @return encrypted string.
     * @throws IllegalArgumentException
     *             if the encrypted string length > 256.
     */
    public static String encrypt(String plainString) {
        try {
            int length = plainString.length();
            if (length >= DEFAULT_PASSWORD_ENCRIPTION_SIZE / 2) {
                length = length + DEFAULT_PASSWORD_ENCRIPTION_SIZE;
            } else {
                length = DEFAULT_PASSWORD_ENCRIPTION_SIZE;
            }
            return (new SWCrypt()).encrypt(plainString, length);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Decrypts string.
     * 
     * @param encryptedString
     *            string to be decrypted.
     * @return decrypted string.
     * @throws IllegalArgumentException
     *             in case checksum is incorrect.
     */
    public static String decrypt(String encryptedString) {
        try {
            return (new SWCrypt()).decrypt(encryptedString);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
