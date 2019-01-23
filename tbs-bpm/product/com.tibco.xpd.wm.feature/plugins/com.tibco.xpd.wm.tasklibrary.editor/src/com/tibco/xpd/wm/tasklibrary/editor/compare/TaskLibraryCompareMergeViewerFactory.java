/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.compare;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.IViewerCreator;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * Factory to contribute to eclipse compare to create merge viewer
 * 
 * @author aallway
 * @since 11 Oct 2010
 */
public class TaskLibraryCompareMergeViewerFactory implements IViewerCreator {

    /**
     * @see org.eclipse.compare.IViewerCreator#createViewer(org.eclipse.swt.widgets.Composite,
     *      org.eclipse.compare.CompareConfiguration)
     * 
     * @param parent
     * @param config
     * @return
     */
    @Override
    public Viewer createViewer(Composite parent, CompareConfiguration config) {
        return new TaskLibraryCompareMergeViewer(parent, SWT.NONE, config);
    }

}
