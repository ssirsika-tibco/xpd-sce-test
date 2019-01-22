/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.xsdtransform.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

/**
 * General Util class for file operations to be used in export and import transformations
 * @author glewis
 *
 */
public class FileUtil {
    /**
     * Returns the String contents of an IFile so we can use for replacement
     * or other functions needed to be performed on it.
     * 
     * @param file
     * @return
     */
    public static String getFileContents(IFile file) throws IOException {
        String stringContents = ""; //$NON-NLS-1$

        StringBuffer sb = new StringBuffer();
        char[] buffer = new char[4000];
        int len;

        InputStreamReader reader;
        try {
            reader =
                    new InputStreamReader(file.getContents(), Charset
                            .forName("UTF-8")); //$NON-NLS-1$
        } catch (CoreException e) {
            throw new IOException(e.getLocalizedMessage());
        }
        try {
            while ((len = reader.read(buffer)) >= 0) {
                sb.append(buffer, 0, len);
            }
        } finally {
            reader.close();
        }
        stringContents = sb.toString();
        return stringContents;
    }
    
    /**
     * Sets the new contents on the file     
     * 
     * @param file
     * @param contents
     */
    public static void setFileContents(IFile file, String contents) throws IOException {
        ByteArrayInputStream input = new ByteArrayInputStream(contents.getBytes());        
        try {
            file.setContents(input, true, false, null);            
            file.refreshLocal(IResource.DEPTH_INFINITE, null);
        } catch (CoreException e) {
            throw new IOException(e.getLocalizedMessage());
        }         
    }
}
