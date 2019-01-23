/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.compare;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.resources.ui.compare.EMFCompareStructureCreator;
import com.tibco.xpd.resources.ui.compare.viewer.EMFCompareMergeViewer;

/**
 * Task library compare merge viewer (the left / right and ancestor views of the
 * task library compare editor).
 * 
 * @author aallway
 * @since 2 Dec 2010
 */
public class TaskLibraryCompareMergeViewer extends
        EMFCompareMergeViewer {

    /**
     * @param parent
     * @param style
     * @param cc
     */
    public TaskLibraryCompareMergeViewer(Composite parent, int style,
            CompareConfiguration cc) {
        super(parent, style, cc);
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.viewer.EMFCompareMergeViewer#createStructureCreator()
     * 
     * @return
     */
    @Override
    protected EMFCompareStructureCreator createStructureCreator() {
        return new TaskLibraryCompareStructureCreator();
    }

}
