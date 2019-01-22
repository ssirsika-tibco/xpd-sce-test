/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.registry;

/**
 * @author nwilson
 */
public final class WsdlFileUtil {
    /**
     * Private constructor.
     */
    private WsdlFileUtil() {
    }

    /**
     * 
     * @param fileName
     *            a string which may or may not end in wsdl
     * @return fileName with .wsdl appended if necessary
     */
    public static String applyWsdlExtension(String fileName) {
        if (!hasWsdlExtension(fileName))
            return fileName + ".wsdl"; //$NON-NLS-1$
        return fileName;
    }

    /**
     * @param fileName
     *            The filename to check.
     * @return true if the extension is a wsdl type.
     */
    public static boolean hasWsdlExtension(String fileName) {
        boolean wsdl = false;
        if (fileName != null) {
            int dot = fileName.lastIndexOf('.');
            if (dot != -1 && dot < (fileName.length() - 1)) {
                String extension = fileName.substring(dot + 1);
                if ("wsdl".equalsIgnoreCase(extension)) { //$NON-NLS-1$
                    wsdl = true;
                }
            }
        }
        return wsdl;
    }

}