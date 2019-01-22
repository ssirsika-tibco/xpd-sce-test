/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.wsdl.ui.internal;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

/**
 * Provides method to calculate checksum for a given File.
 * 
 * @author njpatel
 * @since 3.5
 */
public final class CheckSumUtil {

    /**
     * Calculate the checksum of the given file.
     * 
     * @param file
     * @return checksum or <code>null</code> if it cannot be calculated.
     * @throws IOException
     */
    public static String getCheckSum(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        CheckedInputStream check =
                new CheckedInputStream(inputStream, new CRC32());
        BufferedInputStream in = new BufferedInputStream(check);
        while (in.read() != -1) {
            // Read file in completely
        }

        return check.getChecksum().getValue() + ""; //$NON-NLS-1$
    }
}
