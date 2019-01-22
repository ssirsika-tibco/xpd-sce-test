/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.resources.ui.projectimport;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Declare Studio supported archive project extensions
 * 
 * @author patkinso
 * @since 26 Apr 2013
 */
public class ArchiveFilenameFilter implements FilenameFilter {

    final static private String suffixesStr = ".zip .tar .tar.gz"; //$NON-NLS-1$ 

    final static private String[] acceptableSuffixes = suffixesStr.split("\\s"); //$NON-NLS-1$

    @Override
    public boolean accept(File dir, String name) {
        for (String suffix : acceptableSuffixes) {
            if (name.endsWith(suffix)) {
                return true;
            }
        }

        return false;
    }
}
