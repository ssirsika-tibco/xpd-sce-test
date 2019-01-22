/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.daa.test.comparator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.eclipse.core.runtime.IStatus;

import com.tibco.xpd.core.test.util.WSDLFileComparator;

/**
 * @author kupadhya
 * 
 */
public class TestFileComparator {

    /**
     * @param args
     */
    public static void main(String[] args) {
        WSDLFileComparator wsdlComparator = new WSDLFileComparator();
        File goldFile =
                new File(
                        "C:/src/Studio30Repository/trunk/tests/com.tibco.xpd.n2.test.feature/plugins/com.tibco.xpd.n2.daa.test/gold/ProcIfcNoParamsGOLD.wsdl"); //$NON-NLS-1$
        File generatedFile =
                new File(
                        "C:/src/Studio30Repository/trunk/tests/com.tibco.xpd.n2.test.feature/plugins/com.tibco.xpd.n2.daa.test/generated/ProcIfcNoParams.wsdl"); //$NON-NLS-1$
        InputStream goldFileIS;
        InputStream generatedFileIS;
        String fileName = null;
        try {
            goldFileIS = new FileInputStream(goldFile);
            generatedFileIS = new FileInputStream(generatedFile);
            IStatus compareContents =
                    wsdlComparator.compareContents(goldFileIS,
                            generatedFileIS,
                            fileName);
            if (compareContents != null) {
                System.out.println("Comparison message is " //$NON-NLS-1$
                        + compareContents.getMessage());
            } else {
                System.out.println("Files might be similar"); //$NON-NLS-1$
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
