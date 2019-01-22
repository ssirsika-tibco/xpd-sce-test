/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.resources;

import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.BpmContentLabelProvider;

/**
 * Project explorer content label provider for task library content.
 * 
 * @author aallway
 * @since 3.2
 */
public class TaskLibraryProjectExplorerLabelProvider extends
        BpmContentLabelProvider {

    @Override
    public Image getImage(Object element) {
        /*
         * XPD-1140: Distinguishing between Business, Pageflow and Task Library
         * process type images is now done in Xpdl2WorkingCompyImpl.
         */
        return super.getImage(element);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.BpmContentLabelProvider
     * #getDescription(java.lang.Object)
     */
    @Override
    public String getDescription(Object anElement) {
        /*
         * XPD-1140: Distinguishing between Business, Pageflow and Task Library
         * process type name is now done in Xpdl2WortkingCompyImpl .
         */
        return super.getDescription(anElement);
    }
}
