/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.daa.test.comparator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.tibco.xpd.core.test.util.FileComparator;
import com.tibco.xpd.n2.daa.test.Activator;

/**
 * @author kupadhya
 * 
 */
public class ByteComparator extends FileComparator {

    @Override
    public IStatus compareContents(InputStream goldFileStream,
            InputStream generatedFileStream, String fileName) {

        StringBuilder errorMsg = new StringBuilder();
        BufferedReader goldBR =
                new BufferedReader(new InputStreamReader(goldFileStream));
        BufferedReader generatedBR =
                new BufferedReader(new InputStreamReader(generatedFileStream));
        String expLine = null;
        String genLine = null;
        int lineNo = 0;
        try {

            while ((expLine = goldBR.readLine()) != null) {
                lineNo++;
                expLine = expLine.trim();
                if ((genLine = generatedBR.readLine()) != null) {

                    genLine = genLine.trim();
                    /* Now comparing the expected and generated line */
                    if (compareLine(expLine, genLine) == false) {

                        errorMsg.append(fileName
                                + "::Mismatched at Line Number: " + lineNo //$NON-NLS-1$
                                + "\r\n" + " ExpectedLine:: " + expLine //$NON-NLS-1$ //$NON-NLS-2$
                                + "\r\n" + " GeneratedLine:: " + genLine //$NON-NLS-1$ //$NON-NLS-2$
                                + "\r\n"); //$NON-NLS-1$
                        break;
                    }
                } else {

                    errorMsg.append(fileName
                            + "::!!Generated file does not match with the expected file!!\r\n"); //$NON-NLS-1$
                    break;
                }
            } // while
            if ((genLine = generatedBR.readLine()) != null) {

                /* Trim the extra line to ensure it is not an empty newline */
                genLine.trim();
                /* Ignore empty new line, otherwise return false */
                if (genLine.length() != 0) {

                    errorMsg.append(fileName
                            + "::!!Generated file does not match with the expected file!!\r\n"); //$NON-NLS-1$
                }
            }
        } catch (IOException ioe) {

            errorMsg.append(fileName + "::!!IOException occurred !!" + "\r\n" //$NON-NLS-1$ //$NON-NLS-2$
                    + ioe.getMessage() + "\r\n"); //$NON-NLS-1$
        }
        IStatus toReturn = Status.OK_STATUS;
        if (errorMsg.length() > 0) {

            toReturn =
                    new Status(IStatus.ERROR, Activator.PLUGIN_ID,
                            errorMsg.toString());
        } else {

            toReturn =
                    new Status(
                            IStatus.OK,
                            Activator.PLUGIN_ID,
                            fileName
                                    + "::Generated file contents are identical to the gold file"); //$NON-NLS-1$
        }
        return toReturn;
    }

    protected boolean compareLine(String expectedLine, String generatedLine) {

        if (expectedLine.equals(generatedLine)) {

            return true;
        }
        return false;
    }

    public static void main(String args[]) {

        InputStream goldStream =
                ByteComparator.class.getClassLoader()
                        .getResourceAsStream("byrefproc.gold.scripts"); //$NON-NLS-1$
        InputStream genStream =
                ByteComparator.class.getClassLoader()
                        .getResourceAsStream("byrefproc.gen.scripts"); //$NON-NLS-1$
        ByteComparator comparator = new ByteComparator();
        IStatus compareStatus =
                comparator.compareContents(goldStream, genStream, null);
        if (!compareStatus.isOK()) {

            System.out.println(compareStatus.getMessage());
        } else {

            System.out.println("compare success"); //$NON-NLS-1$
        }
    }
}
