/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.provider;

import org.eclipse.core.resources.IFile;

/**
 * File filters are defined in the Destination Environment extension point and
 * are used to specify which files will be validated for that destination.
 *
 * @author nwilson
 */
public interface IFileFilter {

    /**
     * @param file The file to check.
     * @return true if this file should be validated.
     */
    boolean accept(IFile file);
}
