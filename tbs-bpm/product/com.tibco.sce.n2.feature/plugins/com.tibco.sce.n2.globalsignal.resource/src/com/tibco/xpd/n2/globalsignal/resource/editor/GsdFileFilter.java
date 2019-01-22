/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.editor;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IFilter;

/**
 * Filter to let only GSD files go through.
 * 
 * @author sajain
 * @since Feb 11, 2015
 */
public class GsdFileFilter implements IFilter {

    private static final String GSD_EXTENSION = "gsd"; //$NON-NLS-1$

    @Override
    public boolean select(Object toTest) {

        if (toTest instanceof IFile) {

            IFile file = (IFile) toTest;

            if (GSD_EXTENSION.equals(file.getFileExtension())) {
                return true;
            }

        }
        return false;
    }

}