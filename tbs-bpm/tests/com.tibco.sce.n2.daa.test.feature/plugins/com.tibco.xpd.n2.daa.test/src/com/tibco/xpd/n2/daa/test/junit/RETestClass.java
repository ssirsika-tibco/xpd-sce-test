/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.daa.test.junit;

/**
 * @author kupadhya
 * 
 */
public class RETestClass {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String str = "/ApplicationRequirements[1]/requirements[2]/@id"; //$NON-NLS-1$
        String replaceAll = str.replaceAll("\\[\\d+\\]", ""); //$NON-NLS-1$ //$NON-NLS-2$
        System.out.println("Replace All is " + replaceAll); //$NON-NLS-1$

    }

}
