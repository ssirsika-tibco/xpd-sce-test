/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.resources.util;

import java.util.Date;

/**
 * Provides password encryption/description. This class is for internal use
 * only.
 * <p>
 * <i>Created: 29 Mar 2007</i>
 * </p>
 * 
 * @author Staffware Team, Jan Arciuchiewicz
 * @see StringEncryptionUtil
 */
class SWCrypt {

    public SWCrypt() {
        m_original = ""; //$NON-NLS-1$
        m_originalBites = new byte[256];
    }

    public String encrypt(String plainString, int lenEncryptedString)
            throws Exception {
        byte myByteArray[] = new byte[lenEncryptedString];
        encrypt(plainString, myByteArray, myByteArray.length);
        return new String(myByteArray);
    }

    public void encrypt(String original, byte dest[], int len) throws Exception {
        m_original = original;
        int slen = m_original.length();
        if (256 < m_original.length())
            throw new Exception(
                    "Encryption logic not built to take strings of this size"); //$NON-NLS-1$
        long lCrc_useLow32 = 0L;
        m_originalBites = m_original.getBytes();
        for (int idx = 0; idx < m_original.length(); idx++)
            lCrc_useLow32 = my_crc_calc(lCrc_useLow32, m_originalBites[idx]);

        long seed = (new Date()).getTime() & 7L;
        long seedcopy = seed = seed << 16 | lCrc_useLow32;
        int check = 0;
        int ch = 0;
        for (int idx = 2; idx >= 0; idx--) {
            ch = (int) (seedcopy % 95L);
            check ^= ch;
            dest[idx] = (byte) (ch + 32);
            seedcopy /= 95L;
        }

        dest[3] = (byte) (check % 95 + 32);
        len -= 4;
        for (int idx = 0; idx < len; idx++) {
            byte aBite;
            if (idx < slen)
                aBite = m_originalBites[idx];
            else
                aBite = 32;
            int topbit = aBite & 128;
            aBite &= 127;
            if (aBite >= 32 && aBite <= 126) {
                seed = seed * 1103515245L + 12345L;
                int A = (int) (seed >> 16 & 32767L);
                aBite = (byte) (((byte) (aBite - 32) + (A & 127)) % 95 + 32);
            }
            dest[idx + 4] = (byte) (aBite | topbit);
        }

    }

    public String decrypt(String encryptedString) throws Exception {
        byte myByteArray[] = new byte[encryptedString.length()];
        StringBuffer myStringBuffer = new StringBuffer(
                encryptedString.length() - 4);
        myByteArray = encryptedString.getBytes();
        decrypt(myByteArray, myByteArray.length, myStringBuffer, myStringBuffer
                .capacity());
        return myStringBuffer.toString().trim();
    }

    public void decrypt(byte src[], int srcLen, StringBuffer dest, int len)
            throws Exception {
        long seed = 0L;
        int check = 0;
        int idx = 0;
        int ch = 0;
        for (idx = 0; idx < 3; idx++) {
            ch = src[idx] - 32;
            check ^= ch;
            seed = seed * 95L + (long) ch;
        }

        if ((ch = src[3]) < 0)
            ch += 256;
        if (ch != check % 95 + 32) {
            dest.insert(0, 0);
            throw new Exception("Encryption check byte failed"); //$NON-NLS-1$
        }
        for (idx = 4; idx < srcLen + 4 && idx < len + 4; idx++) {
            ch = src[idx];
            int topbit = ch & 128;
            ch &= 127;
            if (ch >= 32 && ch <= 126) {
                seed = seed * 1103515245L + 12345L;
                int A = (int) (seed >> 16 & 32767L);
                ch = ((ch - 32 - (A & 127)) + 190) % 95 + 32;
            }
            ch |= topbit;
            dest.append((char) ch);
        }

    }

    private long my_crc_calc(long lCrc_useLow32, byte db) {
        long crc = lCrc_useLow32;
        int intBite = 0;
        for (int idx = 0; idx < 8; idx++) {
            if (((long) (db & 1) ^ crc & 1L) == 1L) {
                crc ^= 2064L;
                crc >>= 1;
                crc |= 32768L;
            } else {
                crc >>= 1;
            }
            intBite = db;
            intBite >>= 1;
            db = (byte) intBite;
        }

        return crc;
    }

    private String m_original;

    private byte m_originalBites[];
}