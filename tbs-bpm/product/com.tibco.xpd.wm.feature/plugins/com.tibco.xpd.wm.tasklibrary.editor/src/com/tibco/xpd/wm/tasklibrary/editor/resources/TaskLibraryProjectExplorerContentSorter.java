/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.wm.tasklibrary.editor.resources;

import org.eclipse.jface.viewers.ViewerSorter;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.DataFieldGroup;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.ParticipantGroup;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.TypeDeclarationGroup;

/**
 * Project Explorer uses this to sort items in the package tree view
 * 
 * @author aallway
 * @since 3.2
 */
public class TaskLibraryProjectExplorerContentSorter extends ViewerSorter {

    // Order of appearance in the tree - the lower the number
    // the higher it appears in the tree

    private static final int DATAFIELDS = 1;

    private static final int PARTICIPANTS = 2;

    private static final int TYPEDECLARATIONS = 3;

    private static final int TASKS = 4;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ViewerComparator#category(java.lang.Object)
     */
    @Override
    public int category(Object element) {
        if (element instanceof DataFieldGroup)
            return DATAFIELDS;
        else if (element instanceof ParticipantGroup)
            return PARTICIPANTS;
        else if (element instanceof TypeDeclarationGroup)
            return TYPEDECLARATIONS;
        else if (element instanceof TasksGroup)
            return TASKS;
        else
            return super.category(element);
    }

}
