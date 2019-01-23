/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.sorters;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;

import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.ui.projectexplorer.sorter.IgnoreDirtyMarkerViewerSorter;

/**
 * Used by the Project Explorer to sort resources in the tree. Extends
 * <code>IgnoreDirtyMarkerViewerSorter</code> to ignore dirty markers on
 * resource names.
 * 
 * @author njpatel
 * 
 */
public class ResourceSorter extends IgnoreDirtyMarkerViewerSorter {

    // Define the order of the various objects - the
    // lower the value the higher it appears in the
    // tree
    public static final int PACKAGE_FOLDER = 1;

    public static final int FOLDER = 2;

    public static final int FILE = 3;

    public int category(Object element) {
        if (element instanceof SpecialFolder) {
            return PACKAGE_FOLDER;
        } else if (element instanceof IFolder) {
            return FOLDER;
        } else if (element instanceof IFile) {
            return FILE;
        }

        return super.category(element);
    }

}
