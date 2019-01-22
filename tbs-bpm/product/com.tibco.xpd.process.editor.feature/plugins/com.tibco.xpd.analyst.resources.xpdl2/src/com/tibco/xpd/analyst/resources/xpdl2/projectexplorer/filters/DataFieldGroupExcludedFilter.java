/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.filters;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorConfigurationUtil;
import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorElementType;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.DataFieldGroup;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

/**
 * Exclude data fields group if it has been excluded via the
 * processEditorConfiguration/ElementTypeExclusions ext point - unless the group
 * is not empty in which case always show it.
 * 
 * @author aallway
 * @since 12 Dec 2011
 */
public class DataFieldGroupExcludedFilter extends ViewerFilter {

    /**
     * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param viewer
     * @param parentElement
     * @param element
     * @return
     */
    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {
        boolean include = true;
        if (element instanceof DataFieldGroup) {
            DataFieldGroup dataFieldGroup = (DataFieldGroup) element;

            if (!dataFieldGroup.hasChildren()) {
                /*
                 * If there are no children then we can allow it to be excluded
                 * via processEditorConfig.
                 */
                if (parentElement instanceof Package
                        || parentElement instanceof Process) {
                    if (ProcessEditorConfigurationUtil
                            .getExcludedElementTypes(parentElement)
                            .contains(ProcessEditorElementType.datafield)) {
                        include = false;
                    }
                }
            }
        }
        return include;
    }

}
