/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.core.test.util;

import java.io.InputStream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;

/**
 * @author kupadhya
 * 
 */
public abstract class FileComparator {

    // protected static final Logger LOG = Activator.getDefault().getLogger();

    /**
     * this gives an opportunity to handle timestamps in path, versions
     * 
     * @param inputStream
     * @param project
     * @return
     */
    public InputStream massageInputStream(InputStream inputStream,
            IProject project) {
        return inputStream;
    }

    /**
     * 
     * @param goldFile
     * @param generatedFile
     * @param fileName
     * @return
     */
    public IStatus compareContents(InputStream goldFile,
            InputStream generatedFile, String fileName) {
        return null;
    }

}
